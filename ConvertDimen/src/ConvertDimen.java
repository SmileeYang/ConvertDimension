import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.*;
import org.w3c.dom.*;

public class ConvertDimen {

	static final String sw320dp = "sw320dp";
	static final String sw360dp = "sw360dp";
	static final String sw600dp = "sw600dp";
	static final String sw720dp = "sw720dp";
	static final String sw800dp = "sw800dp";
	static String expression = null, originFilePath = "", savingPath;
	static int inputType;
	static NodeList nodeList;
	static String[] mDimenName;
	static String[] types = new String[] {sw320dp, sw360dp, sw600dp, sw720dp, sw800dp};
	static File xmlFile = null;
	// /Users/smilee_yang/Documents/workspace/SupportDifferentSize/res/values/dimens.xml
	
	public static void main(String[] args) {

		try {
			System.out.print("Please enter the original dimens.xml file path: ");
			do {
				originFilePath = readKeyIn();
				xmlFile = new File(originFilePath);
				if (xmlFile.exists()) {
					int tail = originFilePath.indexOf("/res/"); 
					savingPath = originFilePath.substring(0, tail + 4); // /Users/smilee_yang/Documents/workspace/SupportDifferentSize/res
					System.out.println("savingPath: " + savingPath);
					System.out.println("Correct file, file name: " + xmlFile.getName());
					
					DocumentBuilderFactory docBuFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docBuFactory.newDocumentBuilder();
					Document xmlDoc = docBuilder.parse(xmlFile);
	
					XPath xPath = XPathFactory.newInstance().newXPath();
	
					System.out.println("-------------------------");
	
					// Get dimension name
					expression = "/resources/dimen/attribute::name"; // get abc from <dimen name="abc">123dp</dimen>
					nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDoc, XPathConstants.NODESET);
	
					mDimenName = new String[nodeList.getLength()];
					String[] nowDimenValue = new String[nodeList.getLength()];
	
					for (int i = 0; i < nodeList.getLength(); i++) {
						mDimenName[i] = nodeList.item(i).getFirstChild().getNodeValue();
					}
	
					// Get dimension value
					expression = "/resources/dimen[@name]"; // get 123dp from <dimen name="abc">123dp</dimen>
					nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDoc, XPathConstants.NODESET);
	
					for (int i = 0; i < nodeList.getLength(); i++) {
						nowDimenValue[i] = nodeList.item(i).getFirstChild().getNodeValue();
						System.out.println("mDimenName: "+mDimenName[i]+" & nowDimenValue="+nowDimenValue[i]);
					}
					if (nowDimenValue.length != 0) {
						System.out.println("-------------------------");
						System.out.print("This file is based on - 1.sw320dp 2.sw360dp 3.sw600dp 4.sw720dp 5.sw800dp (plz enter the number): ");
						int densityType = 0;
						do {
							densityType = Integer.valueOf(readKeyIn());
							if (densityType == 1 || densityType == 2 || densityType == 3 || densityType == 4 || densityType == 5) {
								System.out.println("Start convert process, convert from " + types[densityType-1] + " to the others.");
								inputType = densityType;
								filterUnit(nowDimenValue);
							} else {
								System.out.print("Please enter the number either 1 or 2 or 3 or 4 or 5 (1.sw320dp 2.sw360dp 3.sw600dp 4.sw720dp 5.sw800dp): ");
							}
							break;
						} while (densityType != 1 || densityType != 2 || densityType != 3 || densityType != 4 || densityType != 5);
					} else {
						System.out.println("Can't get dimension values, please check your dimens.xml, thanks.");
					}
					System.out.println("Convert process completed.");
	
				} else {
						System.out.print("The path is wrong. Please try again: ");
				}
			} while (!xmlFile.exists());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	public static String readKeyIn() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String keyIn = null; 
		try {
			keyIn = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return keyIn;
	}
	
	public static void filterUnit(String[] nowDimenValue) {
		String[] onlyValue = new String[nowDimenValue.length];
		for (int index = 0; index < nowDimenValue.length; index++) {
			// filter
			String filterUnit = "";
			Pattern pattern = Pattern.compile("[0-9]*(\\.)*[0-9]+");
			Matcher matcher = pattern.matcher(nowDimenValue[index]);
			while (matcher.find()) {
				filterUnit = filterUnit + matcher.group();
			}
			if (!filterUnit.equalsIgnoreCase("")) {
				onlyValue[index] = filterUnit;
//				System.out.println("onlyValue["+index+"]="+onlyValue[index]);
			}
		}
		writingLoop(nowDimenValue, onlyValue);
	}
	
	public static void writingLoop(String[] nowDimenValue, String[] onlyValue) {
		String[][] newDimenValue = new String[onlyValue.length][onlyValue.length];
		int formula = 4;
		Double[] formulaForSw320dp = new Double[] { 1.125, 1.875, 2.25, 2.5 }; // sw320dp to sw360dp, sw600dp, sw720dp, sw800dp
		Double[] formulaForSw360dp = new Double[] { 0.89, 1.67, 2.0, 2.22 }; // sw360dp to sw320dp, sw600dp, sw720dp, sw800dp
		Double[] formulaForSw600dp = new Double[] { 0.53, 0.6, 1.2, 1.33 }; // sw600dp to sw320dp, sw360dp, sw720dp, sw800dp
		Double[] formulaForSw720dp = new Double[] { 0.44, 0.5, 0.83, 1.11 }; // sw720dp to sw320dp, sw360dp, sw600dp, sw800dp
		Double[] formulaForSw800dp = new Double[] { 0.4, 0.45, 0.75, 0.9 }; // sw800dp to sw320dp, sw360dp, sw600dp, sw720dp
		BigDecimal bd;
		for (int times = 0; times < formula; times++) {
			for (int index = 0; index < onlyValue.length; index++) {
				switch (inputType) {
				case 1:
					bd = new BigDecimal(Float.valueOf(onlyValue[index]) * formulaForSw320dp[times]);
					bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
					newDimenValue[times][index] = nowDimenValue[index].replace(onlyValue[index], String.valueOf(bd));
					break;
				case 2:
					bd = new BigDecimal(Float.valueOf(onlyValue[index]) * formulaForSw360dp[times]);
					bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
					newDimenValue[times][index] = nowDimenValue[index].replace(onlyValue[index], String.valueOf(bd));
					break;
				case 3:
					bd = new BigDecimal(Float.valueOf(onlyValue[index]) * formulaForSw600dp[times]);
					bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
					newDimenValue[times][index] = nowDimenValue[index].replace(onlyValue[index], String.valueOf(bd));
					break;
				case 4:
					bd = new BigDecimal(Float.valueOf(onlyValue[index]) * formulaForSw720dp[times]);
					bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
					newDimenValue[times][index] = nowDimenValue[index].replace(onlyValue[index], String.valueOf(bd));
					break;
				case 5:
					bd = new BigDecimal(Float.valueOf(onlyValue[index]) * formulaForSw800dp[times]);
					bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
					newDimenValue[times][index] = nowDimenValue[index].replace(onlyValue[index], String.valueOf(bd));
					break;
				}
//				System.out.println("newDimenValue["+times+"]["+index+"]="+newDimenValue[times][index]);
			}
		}
		bufferedFileWriter(newDimenValue);
	}

	public static void bufferedFileWriter(String[][] newDimenValue) {
		
		int totalTimes = 4;
		int skipType = inputType-1;
		int nowSavingType = 0;
		String fileType = null;
		do {
			fileType = types[nowSavingType];
			if (nowSavingType == skipType) {
				nowSavingType++;
				continue;
			}
			for (int times = 0; times < totalTimes; times++) {
				String mFilePath = savingPath+"/values-"+fileType+"/"; 
				File dir = new File(mFilePath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				Writer fw = null;
				BufferedWriter bw = null;
				String theFront, theDimen = null, theLast;
				try {
					fw = new FileWriter(mFilePath + "dimens.xml", false);
					bw = new BufferedWriter(fw);
					theFront = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n";
					bw.write(theFront);
					bw.newLine();
					for (int i = 0; i < mDimenName.length; i++) {
						theDimen = "<dimen name=\"" + mDimenName[i] + "\">" + newDimenValue[times][i] + "</dimen>";
						bw.write(theDimen);
						bw.newLine();
					}
					theLast = "</resources>";
					bw.write(theLast);
					bw.newLine();
					bw.close();
				} catch (IOException e) {
					System.err.println("Error writing the file : ");
					e.printStackTrace();
				} finally {
					if (bw != null && fw != null) {
						try {
							bw.close();
							fw.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} 
			nowSavingType++;
		} while (nowSavingType < types.length);
	}
}
