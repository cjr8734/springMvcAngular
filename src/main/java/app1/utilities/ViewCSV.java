package app1.utilities;

import au.com.bytecode.opencsv.CSVWriter;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Created by adam on 11/8/2015.
 */
public class ViewCSV extends AbstractView
{
    private String filename;
    private List<String[]> csvData;

    public ViewCSV(String aFilename, List<String[]> aCsvData)
    {
        this.filename = aFilename;
        this.csvData = aCsvData;
    }


    @Override
    protected void renderMergedOutputModel(Map<String, Object> aMap, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception
    {
        CSVWriter writer = new CSVWriter( aResponse.getWriter() );

        MediaType mediaType = new MediaType("text", "csv", Charset.forName("utf-8"));

        try {
            aResponse.setHeader( "Content-Disposition", "attachment; filename=\"" + this.filename + "\"");
            aResponse.setContentType( mediaType.toString() );

            // Write the list of strings to this view
            writer.writeAll(this.csvData);
        }
        finally
        {
            writer.flush();
            writer.close();
        }

    }
}