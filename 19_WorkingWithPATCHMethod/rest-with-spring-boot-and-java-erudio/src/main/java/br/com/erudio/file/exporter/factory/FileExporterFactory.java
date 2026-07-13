package br.com.erudio.file.exporter.factory;

import br.com.erudio.exception.BadRequestException;
import br.com.erudio.file.exporter.contract.FileExporter;
import br.com.erudio.file.exporter.impl.CsvExporter;
import br.com.erudio.file.exporter.impl.XlsxExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileExporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public FileExporter getExporter(String fileName) throws Exception {
        if (fileName.endsWith(".xlsx")){
            return  context.getBean(XlsxExporter.class);
            //return  new XlsxImporter();
        } else if(fileName.endsWith(".csv")) {
            return  context.getBean(CsvExporter.class);
           //return  new CsvImporter();
        } else {
            throw new BadRequestException("Invalid File Format");
        }
    }
}
