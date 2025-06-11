import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        String savePath = "C:\\Users\\levin\\Games\\savegames\\";

        GameProgress gp1 = new GameProgress(100, 2, 5, 333.3);
        GameProgress gp2 = new GameProgress(85, 5, 9, 123.4);
        GameProgress gp3 = new GameProgress(40, 7, 14, 888.8);

        List<String> saveFiles = new ArrayList<>();
        saveFiles.add(savePath + "save1.dat");
        saveFiles.add(savePath + "save2.dat");
        saveFiles.add(savePath + "save3.dat");

        saveGame(saveFiles.get(0), gp1);
        saveGame(saveFiles.get(1), gp2);
        saveGame(saveFiles.get(2), gp3);

        String zipFile = savePath + "saves.zip";
        zipFiles(zipFile, saveFiles);

        deleteFiles(saveFiles);
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            System.out.println("Сохранение выполнено: " + path);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    public static void zipFiles(String zipPath, List<String> files) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (String file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(new File(file).getName());
                    zout.putNextEntry(entry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zout.write(buffer, 0, length);
                    }

                    zout.closeEntry();
                    System.out.println("Файл добавлен в архив: " + file);
                } catch (IOException e) {
                    System.out.println("Ошибка при добавлении в архив: " + file);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании архива: " + e.getMessage());
        }
    }

    public static void deleteFiles(List<String> files) {
        for (String filePath : files) {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("Удалён файл: " + filePath);
            } else {
                System.out.println("Не удалось удалить файл: " + filePath);
            }
        }
    }
}
