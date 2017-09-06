package app1.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;


/**
 * Created by adam on 12/30/2016.
 */
public class ElasticSearchDao
{
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchDao.class);

    private String elasticSearchUrl;
    private AsyncHttpClient asyncHttpClient;

    private Pattern patMatchDoubleQuote     = Pattern.compile("\"");
    private Pattern patMatchAscii1To31or128 = Pattern.compile("[\\u0000-\\u001F\\u0080]");
    private Pattern patMatchBackwardSlashMissingReserveChar = Pattern.compile("\\\\([^+!-><)(:/}{*~]|\\Z)");


    /**************************************************************************
     * cleanupQuery()
     *
     * Clean-up the passed-in raw query with the following rules:
     *  1) If Double quote is found, then replace it with \"
     *  2) If ASCII value between 1 and 31 is found or 128, then replace it with a space
     *  3) If "\" is found without a special reserve chars, then replace it with a space
     ***************************************************************************/
    public String cleanupQuery(String aRawQuery)
    {
        // Convert the pattern match of " to \"
        // NOTE:  Because of Java Regex, you have to use four backward slashes to match a \
        String sCleanedQuery = this.patMatchDoubleQuote.matcher(aRawQuery).replaceAll("\\\\\"");

        // If ASCII 1-31 or 128 is found, then replace it with a space
        sCleanedQuery = this.patMatchAscii1To31or128.matcher(sCleanedQuery).replaceAll(" ");

        // If a single backslash is found but the required reserve char is missing -- then replace it with a space
        sCleanedQuery = this.patMatchBackwardSlashMissingReserveChar.matcher(sCleanedQuery).replaceAll(" ");

        return sCleanedQuery;
    }


    /**************************************************************************
     * ElasticSearchDao()  Constructor
     *
     ***************************************************************************/
    public ElasticSearchDao(String aElasticSearchUrl)
    {
        this.asyncHttpClient = new AsyncHttpClient();
        this.elasticSearchUrl = aElasticSearchUrl;
    }


    /********************************************************************************************
     * getJsonInPrettyFormat()
     *
     * Return the JSON in pretty format
     *********************************************************************************************/
    public static String getJsonInPrettyFormat(String aRawJson)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(aRawJson);
        String prettyJsonString = gson.toJson(je);

        return prettyJsonString;
    }


    /**************************************************************************
     * close()
     ***************************************************************************/
    public void close()
    {
        if (this.asyncHttpClient != null)
        {
            this.asyncHttpClient.close();
        }
    }


    /**************************************************************************
     * runSimpleQueryString()
     *  1) Verify that passed-in arguments are valid
     *  2) Clean-up the query so that quotes do not screw up execution
     *  3) Run a synchronous ES call
     *  4) Verify that the status code is good
     *  5) Return the ES JSON results
     *
     * NOTE:  ElasticSearch uses a zero-based starting record number
     *        So, to see the first 20 results of a search,
     *               aStaringRecordNumber = 0
     *               aPageSize = 20
     *
     * Returned hits (within the JSON) are ordered by the default ES score
     ***************************************************************************/
    public String runSimpleQueryString(String aQuery,
                                       String aIndexName,
                                       long   aStartingRecord,
                                       long   aPageSize) throws Exception
    {
        logger.debug("runSimpleQueryString()  aIndexName={}  aQuery={}", aIndexName, aQuery);

        String sReturnedJson = null;

        if ((aIndexName == null) || (aIndexName.length() == 0))
        {
            throw new RuntimeException("Critical Error in runSimpleQueryString():  The passed-in aIndexName is empty or null.");
        }
        else if (aStartingRecord < 0)
        {
            throw new RuntimeException("Critical Error in runSimpleQueryString():  The passed-in aStartingRecord is invalid:  " + aStartingRecord + ".  This number should be zero or greater");
        }

        // Clean-up the raw query so quotes do not prevent it from working
        String sCleanQuery = cleanupQuery(aQuery);

        // Construct the JSON call to run an ES Simple Query String
        final String sJsonSearchRequest =
                "{ \"explain\": false, " +
                        "\"query\": { " +
                        "\"simple_query_string\": { " +
                        " \"query\": " + "\"" + sCleanQuery + "\"," +
                        "\"default_operator\": \"and\" " +
                        "}" +
                        "}," +
                        "\"from\":" + aStartingRecord + "," +
                        "\"size\":" + aPageSize +
                        "}";

        // Make a synchronous call to ElasticSearch (to run this query)
        Response response = this.asyncHttpClient.preparePost(this.elasticSearchUrl + "/" + aIndexName + "/_search")
                .setHeader("accept", "application/json")
                .setBody(sJsonSearchRequest)
                .execute()
                .get();


        if (response.getStatusCode() != 200)
        {
            // ElasticSearch returned a non-200 status response -- that's bad
            throw new RuntimeException("Critical Error in runSimpleQueryString():  I got a non-200 status code of " + response.getStatusCode() + ".  The error is " + response.getResponseBody());
        }
        else
        {
            // ElasticSearch returned a 200 status -- that's good
            sReturnedJson = response.getResponseBody();
        }


        logger.debug("runSimpleQueryString()  finished.");
        return sReturnedJson;
    }


    /**************************************************************************
     * runSimpleQueryStringOrderBy()
     *  1) Verify that passed-in arguments are valid
     *  2) Clean-up the query so that quotes do not screw up execution
     *  3) Run a synchronous ES call
     *  4) Verify that the status code is good
     *  5) Return the ES JSON results
     *
     * Returned hits (within the JSON) are ordered by the aOrderByField, aOrderByDirection
     ***************************************************************************/
    public String runSimpleQueryStringOrderBy(String aQuery,
                                              String aIndexName,
                                              long   aStartingRecord,
                                              long   aPageSize,
                                              String aOrderByField,
                                              String aOrderByDirection) throws Exception
    {
        logger.debug("runSimpleQueryStringOrderBy()  aIndexName={}  aQuery={}  aOrderByField={}  aOrderByDirection={}", aIndexName, aQuery, aOrderByField, aOrderByDirection);

        String sReturnedJson = null;

        if ((aIndexName == null) || (aIndexName.length() == 0))
        {
            throw new RuntimeException("Critical Error in runSimpleQueryStringOrderBy():  The passed-in aIndexName is empty or null.");
        }
        else if (aStartingRecord < 0)
        {
            throw new RuntimeException("Critical Error in runSimpleQueryStringOrderBy():  The passed-in aStartingRecord is invalid:  " + aStartingRecord + ".  This number should be zero or greater");
        }
        else if ((aOrderByField == null) || (aOrderByField.length() == 0))
        {
            throw new RuntimeException("Critical Error in runSimpleQueryStringOrderBy():  The passed-in aOrderByField is empty or null.");
        }
        else if ((aOrderByDirection == null) || (aOrderByDirection.length() == 0))
        {
            throw new RuntimeException("Critical Error in runSimpleQueryStringOrderBy():  The passed-in aOrderByDirection is empty or null.");
        }

        // Clean-up the raw query so quotes do not prevent it from working
        String sCleanQuery = cleanupQuery(aQuery);

        // Construct the JSON call to run an ES Simple Query String
        final String sJsonSearchRequest =
                "{ \"explain\": false, " +
                        "\"query\": { " +
                        "\"simple_query_string\": { " +
                        "\"query\": " + "\"" + sCleanQuery + "\"," +
                        "\"default_operator\": \"and\" " +
                        "}" +
                        "}," +
                        "\"from\":" + aStartingRecord + "," +
                        "\"size\":" + aPageSize + "," +
                        "\"sort\": [{ \"" + aOrderByField + "\":\"" + aOrderByDirection + "\"}]" +
                        "}";

        // Make a synchronous call to ElasticSearch (to run this query)
        Response response = this.asyncHttpClient.preparePost(this.elasticSearchUrl + "/" + aIndexName + "/_search")
                .setHeader("accept", "application/json")
                .setBody(sJsonSearchRequest)
                .execute()
                .get();


        if (response.getStatusCode() != 200)
        {
            // ElasticSearch returned a non-200 status response -- that's bad
            throw new RuntimeException("Critical Error in runSimpleQueryStringOrderBy():  I got a non-200 status code of " + response.getStatusCode() + ".  The error is " + response.getResponseBody());
        }
        else
        {
            // ElasticSearch returned a 200 status -- that's good
            sReturnedJson = response.getResponseBody();
        }


        logger.debug("runSimpleQueryString()  finished.");
        return sReturnedJson;
    }


    /**************************************************************************
     * runAdvancedQuery()
     *  1) Verify that passed-in arguments are valid
     *  2) Clean-up the query so that quotes do not screw up execution
     *  3) Run a synchronous ES call
     *  4) Verify that the status code is good
     *  5) Return the ES JSON results
     *
     * Returned hits (within the JSON) are ordered by the default ES score
     ***************************************************************************/
    public String runAdvancedQuery(String aQuery,
                                   String aIndexName,
                                   long   aStartingRecord,
                                   long   aPageSize) throws Exception
    {
        logger.debug("runAdvancedQuery()  aIndexName={}  aQuery={}", aIndexName, aQuery);

        String sReturnedJson = null;

        if ((aIndexName == null) || (aIndexName.length() == 0))
        {
            throw new RuntimeException("Critical Error in runAdvancedQuery():  The passed-in aIndexName is empty or null.");
        }
        else if (aStartingRecord < 0)
        {
            throw new RuntimeException("Critical Error in runAdvancedQuery():  The passed-in aStartingRecord is invalid:  " + aStartingRecord + ".  This number should be zero or greater");
        }

        // Clean-up the raw query so quotes do not prevent it from working
        String sCleanQuery = cleanupQuery(aQuery);

        // Construct the JSON call to run an ES Advanced Query String
        final String sJsonSearchRequest =
                "{  \"explain\": false, " +
                        "\"query\": { " +
                        "\"query_string\": { " +
                        "\"query\": " + "\"" + sCleanQuery + "\"" +
                        "}" +
                        "}," +
                        "\"from\":" + aStartingRecord + "," +
                        "\"size\":" + aPageSize +
                        "}";

        // Make a synchronous call to ElasticSearch (to run this query)
        Response response = this.asyncHttpClient.preparePost(this.elasticSearchUrl + "/" + aIndexName + "/_search")
                .setHeader("accept", "application/json")
                .setBody(sJsonSearchRequest)
                .execute()
                .get();


        if (response.getStatusCode() != 200)
        {
            // ElasticSearch returned a non-200 status response -- that's bad
            throw new RuntimeException("Critical Error in runAdvancedQuery():  I got a non-200 status code of " + response.getStatusCode() + ".  The error is " + response.getResponseBody());
        }
        else
        {
            // ElasticSearch returned a 200 status -- that's good
            sReturnedJson = response.getResponseBody();
        }


        logger.debug("runAdvancedQuery()  finished.");
        return sReturnedJson;
    }



    /**************************************************************************
     * runAdvancedQuery()
     *  1) Verify that passed-in arguments are valid
     *  2) Clean-up the query so that quotes do not screw up execution
     *  3) Run a synchronous ES call
     *  4) Verify that the status code is good
     *  5) Return the ES JSON results
     *
     * Returned hits (within the JSON) are ordered by the aOrderByField, aOrderByDirection
     ***************************************************************************/
    public String runAdvancedQueryOrderBy(String aQuery,
                                          String aIndexName,
                                          long   aStartingRecord,
                                          long   aPageSize,
                                          String aOrderByField,
                                          String aOrderByDirection) throws Exception
    {
        logger.debug("runAdvancedQueryOrderBy()  aIndexName={}  aQuery={}", aIndexName, aQuery);

        String sReturnedJson = null;

        if ((aIndexName == null) || (aIndexName.length() == 0))
        {
            throw new RuntimeException("Critical Error in runAdvancedQueryOrderBy():  The passed-in aIndexName is empty or null.");
        }
        else if (aStartingRecord < 0)
        {
            throw new RuntimeException("Critical Error in runAdvancedQueryOrderBy():  The passed-in aStartingRecord is invalid:  " + aStartingRecord + ".  This number should be zero or greater");
        }
        else if ((aOrderByField == null) || (aOrderByField.length() == 0))
        {
            throw new RuntimeException("Critical Error in runAdvancedQueryOrderBy():  The passed-in aOrderByField is empty or null.");
        }
        else if ((aOrderByDirection == null) || (aOrderByDirection.length() == 0))
        {
            throw new RuntimeException("Critical Error in runAdvancedQueryOrderBy():  The passed-in aOrderByDirection is empty or null.");
        }

        // Clean-up the raw query so quotes do not prevent it from working
        String sCleanQuery = cleanupQuery(aQuery);

        // Construct the JSON call to run an ES Advanced Query String
        final String sJsonSearchRequest =
                "{  \"explain\": false, " +
                        "\"query\": { " +
                        "\"query_string\": { " +
                        "\"query\": " + "\"" + sCleanQuery + "\"" +
                        "}" +
                        "}," +
                        "\"from\":" + aStartingRecord + "," +
                        "\"size\":" + aPageSize + "," +
                        "\"sort\": [{ \"" + aOrderByField + "\":\"" + aOrderByDirection + "\"}]" +
                        "}";

        // Make a synchronous call to ElasticSearch (to run this query)
        Response response = this.asyncHttpClient.preparePost(this.elasticSearchUrl + "/" + aIndexName + "/_search")
                .setHeader("accept", "application/json")
                .setBody(sJsonSearchRequest)
                .execute()
                .get();


        if (response.getStatusCode() != 200)
        {
            // ElasticSearch returned a non-200 status response -- that's bad
            throw new RuntimeException("Critical Error in runAdvancedQuery():  I got a non-200 status code of " + response.getStatusCode() + ".  The error is " + response.getResponseBody());
        }
        else
        {
            // ElasticSearch returned a 200 status -- that's good
            sReturnedJson = response.getResponseBody();
        }


        logger.debug("runAdvancedQueryOrderBy()  finished.");
        return sReturnedJson;
    }


    /**************************************************************************
     * isElasticSearchRunning()
     *
     * Returns TRUE if the ElasticSearch instance is up and running
     * Returns FALSE otherwise
     ***************************************************************************/
    public boolean isElasticSearchRunning()
    {
        try
        {
            // Make a synchronous call to ElasticSearch (to run this query)
            Response response = this.asyncHttpClient.prepareGet(this.elasticSearchUrl + "/")
                    .setHeader("accept", "application/json")
                    .execute()
                    .get();

            if (response.getStatusCode() != 200)
            {
                // ElasticSearch returned a non-200 status response -- that's bad
                logger.warn("Warning in isElasticSearchRunning():  The call to see if ES returned a non-200 status code of {} and text of {}", response.getStatusCode(), response.getResponseBody());
                return false;
            }

            // ElasticSearch returned a 200 status -- so we assume it is up and running
            return(true);
        }
        catch (Exception e)
        {
            // I got some sort of exception -- so assume it is not running
            logger.warn("Warning in isElasticSearchRunning():  The call to see if ES is running threw an exception.  ", e);
            return(false);
        }
    }


}