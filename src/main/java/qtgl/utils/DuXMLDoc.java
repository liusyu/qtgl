package qtgl.utils;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class DuXMLDoc {
	
	public static <T> T  parse( Class<T> cls,String protocolXML) {
		try {
			SAXParserFactory saxfac = SAXParserFactory.newInstance();
			SAXParser saxparser = saxfac.newSAXParser();
			TestSAX tsax = new TestSAX();
			saxparser.parse(new InputSource(new StringReader(protocolXML)),tsax);
			//System.out.print(tsax.getMap().size());
			return MapToObject.setFieldValue(tsax.getMap(),cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
 }

class TestSAX extends DefaultHandler {

	private StringBuffer buf;
	private String str;
	private Map<String, String> map=new HashMap<String, String>();

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public TestSAX() {
		super();
	}

	public void startDocument() throws SAXException {
		buf = new StringBuffer();
		// System.out.println("*******开始解析XML*******");
	}

	public void endDocument() throws SAXException {
		// System.out.println("*******XML解析结束*******");
	}

	public void endElement(String namespaceURI, String localName,String fullName) throws SAXException {
		str = buf.toString();
		// System.out.println("节点="+fullName+"\tvalue="+buf+" 长度="+buf.length());
		// System.out.println();
		if(buf.length()>0)
		{
			map.put(fullName, buf.toString());	
		}
		
		buf.delete(0, buf.length());
	}

	public void characters(char[] chars, int start, int length)throws SAXException {
		// 将元素内容累加到StringBuffer中
		buf.append(chars, start, length);
	}

}