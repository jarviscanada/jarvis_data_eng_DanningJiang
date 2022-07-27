package ca.jrvs.apps.grep;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.log4j.BasicConfigurator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JavaGrepImp implements JavaGrep{

    final Logger logger = LoggerFactory.getLogger((JavaGrep.class));
    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {
        if (args.length != 3){
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        //Use default logger config
        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }
    }

    /**
     * Top level search workflow
     * @throws IOException
     */
    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<>();

        for (File file : listFiles(getRootPath())){
            for (String line : readLines(file)){
                if(containsPattern(line)){
                    matchedLines.add(line);
                }
            }
        }

        writeToFile(matchedLines);

    }

    /**
     * Traverse a given directory and return all files
     * @param rootDir input directory
     * @return files under the rootDir
     */
    @Override
    public List<File> listFiles(String rootDir) {
        List<File> allFiles = new ArrayList<File>();
        File curDir = new File(rootDir);
        if(curDir.listFiles() != null){ //curDir.listFiles() return File[] object
            for(File f : curDir.listFiles()){
                if(f.isFile()){
                    allFiles.add(f);
                }else if(f.isDirectory()){ //if it's a dir, going through the whole path
                    allFiles.addAll(listFiles(f.getAbsolutePath()));
                }

            }
        }

        return allFiles;
    }

    /**
     *Read a file and return all the lines
     * @param inputFile file to read
     * @return all the lines
     * @throws IllegalArgumentException if a given inputFile is not a file
     */
    @Override
    public List<String> readLines(File inputFile) throws IllegalArgumentException{
        List<String> lines = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String curLine;
            while( (curLine = reader.readLine()) != null){
                lines.add(curLine);
            }
            reader.close();
        }catch (IOException ex){
            throw new IllegalArgumentException("Input file is not a file");
        }
        return lines;
    }
    /**
     * check if a line contains the regex pattern (passed by user)
     * @param line input string
     * @return true if there is a match
     */
    @Override
    public boolean containsPattern(String line) {
        return line.matches(getRegex());
    }
    /**
     * Write lines to a file
     * FileOutputStream: Opens a stream to write data to a file.
     * OutputStreamWriter: Is a stream that converts character streams to byte stream using a charset.
     * BufferedWriter: Writes text to a character stream. Buffered to increase efficiency.
     *
     * @param lines matched line
     * @throws IOException if write failed
     */
    @Override
    public void writeToFile(List<String> lines) throws IOException {
        try {
            FileWriter fw = new FileWriter(getOutFile());
            BufferedWriter bufWriter = new BufferedWriter(fw);
            for (String line : lines){
                bufWriter.write(line + "\n");
            }
            bufWriter.close();
        }catch (IOException e){
            logger.error("Write Failed",e);
        }


    }

    //Getters and Setters
    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}
