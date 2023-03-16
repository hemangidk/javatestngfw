package com.orangehrm.pageFactory.util;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.List;

public class custUtil {

    protected javaScriptUtil js = new javaScriptUtil();

    public static int GetRandomInteger() {
        Random r1 = new Random();
        return r1.nextInt(1000);
    }

    public void clearDirectory(String Dir) throws NullPointerException {
        File file = new File(Dir);
        String[] myFiles;

        try {
            if (file.isDirectory()) {
                myFiles = file.list();
                //			for (int i = 0; i < myFiles.length; i++) {
                assert myFiles != null;
                for (String name : myFiles) {
                    File myFile = new File(file, name);
                    //noinspection ResultOfMethodCallIgnored
                    myFile.delete();
                }
            } else if (file.isFile()) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }

    }

    public static void DeleteDirectoryRecursively(String DirPath) throws IOException {
//		String DirPath = "D:\\test";
        Path directory = Paths.get(DirPath);
        System.out.println("Deleting folder: " + directory);

        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                Files.delete(file); // this will work because it's always a File
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir); // this will work because Files in the directory are already deleted
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public void getAllCookies(WebDriver driver, String sFileNameWithPath) {
//		File file = new File("d:\\a\\Cookies1.data");
        File file = new File(sFileNameWithPath);
        try {
            // Delete old file if exists
            //noinspection ResultOfMethodCallIgnored
            file.delete();
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            FileWriter fileWrite = new FileWriter(file);
            BufferedWriter Bwrite = new BufferedWriter(fileWrite);
            // loop for getting the cookie information

            // loop for getting the cookie information
            for (Cookie ck : driver.manage().getCookies()) {
                Bwrite.write((ck.getName() + ";" + ck.getValue() + ";" + ck.getDomain() + ";" + ck.getPath() + ";"
                        + ck.getExpiry() + ";" + ck.isSecure()));
                Bwrite.newLine();
            }
            Bwrite.close();
            fileWrite.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void uploadFileUsingRobot(String sFilename) throws AWTException, InterruptedException {
        Thread.sleep(1000);
        StringSelection ss = new StringSelection(sFilename);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

        Robot robot = new Robot();

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(1000);
    }

    public void checkToastrDisplayed(WebElement ToastrLoc) {
        int Cnt = 0;
        try {
            while (!ToastrLoc.isDisplayed() && Cnt <= 30) {
                //noinspection BusyWait
                Thread.sleep(1000);
                Cnt++;
                System.out.println("waiting for toastr" + ToastrLoc.getTagName() + " elapsed time in sec: " + Cnt);
            }
        } catch (Exception e) {
            System.out.println(e + " -- Could not find the toaster message");
        }
    }

//	public void waitForBouncerDisapper(WebDriver driver) throws InterruptedException {
//		String sLoc = "//div[@class=\"gray-bg\"]//div[@class=\"ibox-content sk-loading\"]/div[@class=\"sk-spinner sk-spinner-double-bounce\"]/div";
//		int Cnt = 0;
//
//		while (driver.findElements(By.xpath(sLoc)).size() > 0 && Cnt <= 10) {
//			Thread.sleep(1000);
//			Cnt++;
//			System.out.println("waiting for bouncer to dissappear. Elapsed time in sec: " + Cnt);
//		}
////		System.out.println("");
//	}


    public void waitForElementToDisapper(WebDriver driver, By Byele) throws InterruptedException {
        int Cnt = 0;
//        By btest = By.xpath("//div[contains(text(),'Loading...')]");
        while (driver.findElements(Byele).size() > 0 && Cnt <= 60) {
            //noinspection BusyWait
            Thread.sleep(1000);
            Cnt++;
            System.out.println("waiting for bouncer to dissappear. Elapsed time in sec: " + Cnt);
        }
//		System.out.println("");
    }

    public boolean isAlertPresent(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private boolean acceptNextAlert = true;

    public String closeAlertAndGetItsText(WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

    public boolean VerifyTextContainsOnPage(WebDriver driver, String textToVerify) {
        driver.getPageSource();
        return driver.getPageSource().contains(textToVerify);
    }


    public boolean verifyElementNotDisplayed(WebDriver driver, WebElement ele) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        boolean bstatus = verifyElementNotDisplayed(ele);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return bstatus;
    }

    public boolean verifyElementNotDisplayed(WebElement ele) {
        try {
            return (!ele.isDisplayed());
        } catch (Exception e) {
            return false;
        }
    }

    public static String getCurrentDateTimeStamp() {
        Date objDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MMM_dd_hh_mm_ss");
        objDate = new Date();
        // Current System Date and time is assigned to objDate
        return (sdf.format(objDate));
    }

    public static String getCurrentDate() {
        Date objDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MMM_dd");
        objDate = new Date();
        // Current System Date and time is assigned to objDate
        return (sdf.format(objDate));
    }
    public void scrollToElement(WebDriver driver, WebElement ele) {

        try {
            js.execute_js(driver, "arguments[0].scrollIntoView();", ele);
        } catch (Exception e) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(e);
        }
    }
    public static void pageScrollDown(WebDriver driver)
    {
        try {
        Thread.sleep(1000);
        javaScriptUtil js = new javaScriptUtil();
        js.execute_js(driver,"window.scrollBy(0,600)");
        Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    public void click(WebDriverWait mywait, WebElement ele) {
        try {
            mywait.until(ExpectedConditions.visibilityOf(ele));
            mywait.until(ExpectedConditions.elementToBeClickable(ele));
            ele.click();
        } catch (Exception e) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(e);
        }
    }

    public static void captureScreenShot(WebDriver driver, String Message) {
      try {
          File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

          String encodedBase64 = null;

          FileInputStream fileInputStreamReader;
          try {
              fileInputStreamReader = new FileInputStream(scrFile);
              byte[] bytes = new byte[(int) scrFile.length()];
              fileInputStreamReader.read(bytes);
              encodedBase64 = new String(Base64.encodeBase64(bytes));

        String sImageFile = "data:image/png;base64," + encodedBase64;

        Reporter.log("capturing screen shot: " + Message, true);
        Reporter.log(String.valueOf(MediaEntityBuilder.createScreenCaptureFromPath(sImageFile).build()));
        testBed.test.log(Status.PASS, "capturing screen shot: " + Message, MediaEntityBuilder.createScreenCaptureFromPath(sImageFile).build());
          } catch ( FileNotFoundException e) {
              e.printStackTrace();
          }
      }
      catch (IOException e) {
          e.printStackTrace();
      }
    }

    public static void sendEmailReport(String fileName, List<String> summary) {

        // Create object of Property file
        Properties props = new Properties();

        // this will set host of server- you can change based on your requirement
        props.put("mail.smtp.host", "smtp.gmail.com");

        // set the port of SMTP server
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        // set socket factory
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // set the authentication to true
//        props.put("mail.ssl.auth", "true");
        props.put("mail.smtp.auth", "true");


        // This will handle the complete authentication
        Session session = Session.getDefaultInstance(props,

                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("wfhuser2@gmail.com", "zyexykmcplgxfkvk");
                    }

                });
        try {
            String SystemDetails;
            // Create object of MimeMessage class
            Message message = new MimeMessage(session);
            try {
                SystemDetails = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
            // Set the from address
            message.setFrom(new InternetAddress("wfhuser2@gmail.com"));
            // Set the recipient address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("wfhuser1@gmail.com"));

            // Add the subject link
            message.setSubject("**** UI Atmtn Rpt - Device: "+SystemDetails+" tmpstmp: " + getCurrentDateTimeStamp() + " On Browser: "+ testBed.TestBedBrowser+ "****");

            // Create object to add multimedia type content
            BodyPart messageBodyPart1 = new MimeBodyPart();
            String tmp ="";
            for (String s:summary
                 ) {
                tmp = tmp +s+"\t";
            }
            String sMessage = tmp;
            // Set the body of email
            messageBodyPart1.setText(sMessage);
            // Create another object to add another content
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            // Mention the file which you want to send
//            String filename = System.getProperty("user.dir") + "/Results/emailable-report.html";

            // Create data source and pass the filename
            DataSource source = new FileDataSource(fileName);
            // set the handler
            messageBodyPart2.setDataHandler(new DataHandler(source));
            // set the file
            messageBodyPart2.setFileName(fileName);

            // Create object of MimeMultipart class
            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart2);
            multipart.addBodyPart(messageBodyPart1);

            // set the content
            message.setContent(multipart);
            // finally send the email
            Transport.send(message);
//
            System.out.println("=====Email Sent=====");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
    public void CookiesTest(WebDriver driver) {
        String TempFolder = System.getProperty("user.dir") + "/Temp";
        new File(TempFolder).mkdir();
        String FileName = TempFolder + "/Cookie_" + custUtil.getCurrentDateTimeStamp() + ".txt";

        Set<Cookie> cookies = driver.manage().getCookies();

        System.out.println("__________________");
        for (Cookie ck : cookies) {
            fileUtil.writeLineToFile(FileName, "Name: " + ck.getName());
            fileUtil.writeLineToFile(FileName, "Value: " + ck.getValue());
            fileUtil.writeLineToFile(FileName, "Domain: " + ck.getDomain());
            fileUtil.writeLineToFile(FileName, "Path: " + ck.getPath());
            fileUtil.writeLineToFile(FileName, "Expiary: " + ck.getExpiry());
            fileUtil.writeLineToFile(FileName, "Is Secure: " + ck.isSecure());
            fileUtil.writeLineToFile(FileName, "__________________");
        }
    }

}