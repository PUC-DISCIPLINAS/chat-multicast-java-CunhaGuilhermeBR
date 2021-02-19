package persistencia;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileSystem {


    // FileSystem é a classe que escreve e lê os dados em arquivos. Com os metódos estáticos qualquer classe
    // pode gravar e ler dados usando a FileSystem.
    public static void gravar(String data, String path) {
        try {
            File file = new File(path);
          
            FileWriter fileWriter = new FileWriter(path, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(data);
            writer.flush();
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(String path){
        File file = new File(path);
        if (file.exists() ){ // Ele verifica se o arquivo já existe, caso exista ele apaga o anterior
            file.delete();
        }
    }


    public static List<String> ler(String path) {
        ArrayList<String> data = new ArrayList();
        String line;
        try {
            FileReader fi = new FileReader(path);
            BufferedReader reader = new BufferedReader(fi);
            line = reader.readLine();
            while(line != null) {
                data.add(line + ",");
                line = reader.readLine();
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            return data;
        }
    }

}