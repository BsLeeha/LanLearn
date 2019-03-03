// Old File IO: File
// NIO(JDK7): Files, Path: file cp, mv, recursively iterate, delete

import java.io.*;
import java.nio.Buffer;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.Scanner;

import static java.lang.System.out;
import static java.lang.System.setOut;

// sout: System.out.println
// FileOutputStream will create the file if the parent dirs exist
// best practices? mkdirs -> createNewFile -> FileOutputStream ?

class FileTest {
    public static void run() {
        /*------------ merge binary temp files ----------- */

        // create local file
        File inFile = new File(System.getProperty("user.home") + "/Documents/Dir1/Dir2/in.txt");
        inFile.getParentFile().mkdirs();            // mkdirs: recursively mkdirs
        try {
            if (inFile.exists()) inFile.delete();    // just for clear
            inFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create 10 temp files
        for (int i = 0; i < 10; i++) {
            File tempFile = new File(inFile + "." + i + ".tmp");
            // don't have to create the temp file explicitly here
            try(var out = new FileOutputStream(tempFile)) {
                    out.write(new byte[]{'a', 'b', 'c', 'd', '\n'});
                    out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // merge 10 temp files
        for (int i = 0; i < 10; i++) {
            File tempFile = new File(inFile + "." + i + ".tmp");

            try (var in = new FileInputStream(tempFile)) {
                try (var out = new FileOutputStream(inFile, true)) {    // append
                    byte[] bytes = new byte[1024];
                    int n;
                    while ((n = in.read(bytes)) != -1) {
                        out.write(bytes, 0, n);      // work with append mode
                        out.flush();
                    }
                }
                tempFile.delete();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*------ merge text temp files ---------*/

        // create local file
        Path inFile1 = Paths.get(System.getProperty("user.home"), "Documents", "Dir2", "Dir3", "in.txt");
        Path tempFile;
        try {
            Files.createDirectories(inFile1.getParent());       // recursively create dirs
            if (Files.exists(inFile1))
                Files.delete(inFile1);          // clear content
            Files.createFile(inFile1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create 10 temp files
        for (int i = 0; i < 10; i++) {
            tempFile = Paths.get(inFile1 + "." + i + ".tmp");
            // don't have to create the temp file explicitly here
            try (PrintWriter out = new PrintWriter(tempFile.toString())) {
                out.println("Hello World!");
                out.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // merge 10 temp files
        for (int i = 0 ; i < 10; i++) {
            tempFile = Paths.get(inFile1 + "." + i + ".tmp");
            try (var in = new Scanner(tempFile)) {
                try (var out = new PrintWriter(new FileOutputStream(inFile1.toString(), true))) {   // to append
                    String tmp;
                    while (in.hasNextLine()) {
                        tmp = in.nextLine();
                        out.println(tmp);
                        out.flush();
                    }
                }
                Files.delete(tempFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void listFileRec(File file, String space) {
        System.out.println(space + "---" + file.getName());
        space += "\t";
        for (File f : file.listFiles()) {
            if (f.isDirectory())
                listFileRec(f, space);
            else
                System.out.println(space + "---" + f.getName());
        }
    }

    public static void deleteDirRec(File file) {
        // first recursively delete the files in the dir
        if (file.isDirectory()) {
            for (File f : file.listFiles())
                deleteDirRec(f);
        }

        // then delete the empty dir
        file.delete();
    }
}

class PathTest {
    public static void run() {
        Path path = FileSystems.getDefault().getPath("./bs/lee", "abc.txt");
        System.out.println(path.getNameCount());

        Path path1 = new File("./bs/lee/abc.txt").toPath();
        System.out.println(path1.compareTo(path));

        Path path2 = Paths.get("./bs/lee", "abc.txt");
        System.out.println(path2.toString());

        Path path3 = Paths.get("./bs/lee");
        System.out.println("path3: " + path3.resolve("abc.txt"));       // path3 must be dir to use resolve

        if (Files.isReadable(path)) {
            System.err.println("it is Readable");
        } else {
            System.err.println("it is not Readable");
        }
    }
}

class FilesPathTest {
    public static void run() {
        out.printf("System directory separator: %s, System path separator: %s\n",
                File.separatorChar, File.pathSeparatorChar);

        String encode = System.getProperty("file.encoding");
        out.println(encode);

        try {
            Path path = Paths.get("/home", "lee", "Documents", "test.txt");
            if (Files.notExists(path)) Files.createFile(path);
            byte[] bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class FilesTest {
    public static void moveFile() {
        Path from = Paths.get("./bs/lee", "abc.txt");
        Path to   = from.getParent().resolve("abcd.txt");            // parent is ./bs/lee, to use resolve, must be dir
        try {
            System.out.println(Files.size(from));
            Files.move(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileAttributes(String filePath) {
        Path path = Paths.get(filePath);
        System.out.println(Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS));
        try {
            BasicFileAttributes fileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
            System.out.println(fileAttributes.isDirectory());
            System.out.println(new Date(fileAttributes.lastModifiedTime().toMillis()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createDir() {
        Path path = Paths.get("./bs/lee");
        try {
            if (Files.notExists(path)) {
                Files.createDirectories(path);
                System.out.println(path + " created!");
            } else {
                System.out.println(path + " exists!");
            }

            Path path1 = path.resolve("A.java");
            Path path2 = path.resolve("B.java");
            Path path3 = path.resolve("C.txt");
            Path path4 = path.resolve("D.jpg");

            Files.createFile(path1);
            Files.createFile(path2);
            Files.createFile(path3);
            Files.createFile(path4);

            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path, "*.{java,txt}");
            for (Path p : directoryStream) {
                System.out.println(p.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class ZipOp {
    public static void zipList(String zipName) {
        try {
            FileSystem fileSystem = FileSystems.newFileSystem(Paths.get(zipName), null);
            Files.walkFileTree(fileSystem.getPath("/"), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    out.println(file);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zip(String zipName) {

    }

    public static void unzip(String zipName) {

    }
}

class FileSystemTest {
    public static void run() {
        FileSystem fileSystem = FileSystems.getDefault();

        out.println("Default File System ReadOnly? " + fileSystem.isReadOnly());
        out.println("Default File System's separator? " + fileSystem.getSeparator());

        out.println();

        for (FileStore fileStore : fileSystem.getFileStores()) {
            String desc = fileStore.toString();
            String type = fileStore.type();
            try {
                long totalSpace = fileStore.getTotalSpace();
                long unallocatedSpace = fileStore.getUnallocatedSpace();
                long availableSpace = fileStore.getUsableSpace();

                out.println(desc);
                out.println("\tType: " + type);
                out.println("\tTotal Space: " + unify(totalSpace));
                out.println("\tUnallocated Space: " + unify(unallocatedSpace));
                out.println("\tAvailable Space: " + unify(availableSpace));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        out.println("\nPath roots:");

        for (Path root : fileSystem.getRootDirectories()) {
            out.println(root);
        }
    }

    public static String unify(long data) {
        String[] unit = {" B", " KB", " MB", " GB"};
        long tmp = data;
        int i;

        for (i = 0; i < unit.length; i++) {
            if((tmp / 1024) == 0)
                return tmp + unit[i];
            else
                tmp /= 1024;
        }

        return tmp*1024 + unit[i-1];
    }
}


public class FileOp {
    public static void main(String[] args) {

        FileTest.run();
    }


    public static void fileList(String path) {
        // list all files with dir path
        try {
            Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
