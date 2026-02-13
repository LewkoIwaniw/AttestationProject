package ua.inf.iwanoff.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public final class JarResources {
    private boolean debugOn = false;
    private Hashtable<String, Integer> htSizes = new Hashtable<>();
    private Hashtable<String, byte[]> htJarContents = new Hashtable<>();
    private String jarFileName;

    public JarResources(String jarFileName) {
        this.jarFileName = jarFileName;
        init();
    }

    public byte[] getResource(String name) {
        return (byte[]) htJarContents.get(name);
    }

    private void init() {
        ZipInputStream zis = null;
        try {
            // extracts just sizes only. 
            ZipFile zf = new ZipFile(jarFileName);
            Enumeration<?> e = zf.entries();
            while (e.hasMoreElements()) {
                ZipEntry ze = (ZipEntry) e.nextElement();
                if (debugOn) {
                    System.out.println(dumpZipEntry(ze));
                }
                htSizes.put(ze.getName(), new Integer((int) ze.getSize()));
            }
            zf.close();

            // extract resources and put them into the hashtable.
            FileInputStream fis = new FileInputStream(jarFileName);
            BufferedInputStream bis = new BufferedInputStream(fis);
            zis = new ZipInputStream(bis);
            ZipEntry ze = null;
            while ((ze = zis.getNextEntry()) != null) {
                if (ze.isDirectory()) {
                    continue;
                }
                if (debugOn) {
                    System.out.println("ze.getName()=" + ze.getName() + ","
                            + "getSize()=" + ze.getSize());
                }
                int size = (int) ze.getSize();
                // -1 means unknown size. 
                if (size == -1) {
                    size = ((Integer) htSizes.get(ze.getName())).intValue();
                }
                byte[] b = new byte[(int) size];
                int rb = 0;
                int chunk = 0;
                while (((int) size - rb) > 0) {
                    chunk = zis.read(b, rb, (int) size - rb);
                    if (chunk == -1) {
                        break;
                    }
                    rb += chunk;
                }
                // add to internal resource hashtable
                htJarContents.put(ze.getName(), b);
                if (debugOn) {
                    System.out.println(ze.getName() + "  rb=" + rb + ",size=" + size
                            + ",csize=" + ze.getCompressedSize());
                }
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("done.");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                zis.close();
            }
            catch (IOException exx) {

                exx.printStackTrace();
            }
        }
    }

    private String dumpZipEntry(ZipEntry ze) {
        StringBuffer sb = new StringBuffer();
        if (ze.isDirectory()) {
            sb.append("d ");
        }
        else {
            sb.append("f ");
        }
        if (ze.getMethod() == ZipEntry.STORED) {
            sb.append("stored   ");
        }
        else {
            sb.append("defalted ");
        }
        sb.append(ze.getName());
        sb.append("\t");
        sb.append("" + ze.getSize());
        if (ze.getMethod() == ZipEntry.DEFLATED) {
            sb.append("/" + ze.getCompressedSize());
        }
        return (sb.toString());
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("usage: java JarResources <resource name>");
            System.exit(1);
        }
        JarResources jr = new JarResources("pictures.jar");
        byte[] buff = jr.getResource(args[0]);
        if (buff == null) {
            System.out.println("Could not find " + args[0] + ".");
        }
        else {
            System.out
                    .println("Found " + args[0] + " (length=" + buff.length + ").");
        }
    }
}