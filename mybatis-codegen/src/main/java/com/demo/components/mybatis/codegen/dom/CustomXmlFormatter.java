//package com.demo.components.mybatis.codegen.dom;
//
//import org.mybatis.generator.api.dom.DefaultXmlFormatter;
//import org.mybatis.generator.api.dom.OutputUtilities;
//import org.mybatis.generator.api.dom.xml.Attribute;
//import org.mybatis.generator.api.dom.xml.Document;
//import org.mybatis.generator.api.dom.xml.Element;
//import org.mybatis.generator.api.dom.xml.XmlElement;
//
//
///**
// * 自定义格式化XML
// *
// * @Author wude
// * @Create 2019-06-29 9:50
// */
//public class CustomXmlFormatter extends DefaultXmlFormatter {
//
//    public CustomXmlFormatter() {
//        super();
//    }
//
//    @Override
//    public String getFormattedContent(Document document) {
//        return getDocumentFormattedContent(document);
//    }
//
//    /**
//     * Gets the formatted content.
//     *
//     * @return the formatted content
//     */
//    public String getDocumentFormattedContent(Document document) {
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); //$NON-NLS-1$
//
//        if (document.getPublicId() != null && document.getSystemId() != null) {
//            OutputUtilities.newLine(sb);
//            sb.append("<!DOCTYPE "); //$NON-NLS-1$
//            sb.append(document.getRootElement().getName());
//            sb.append(" PUBLIC \""); //$NON-NLS-1$
//            sb.append(document.getPublicId());
//            sb.append("\" \""); //$NON-NLS-1$
//            sb.append(document.getSystemId());
//            sb.append("\">"); //$NON-NLS-1$
//        }
//
//        OutputUtilities.newLine(sb);
//        sb.append(getXmlElementFormattedContent(document.getRootElement(), 0));
//
//        return sb.toString();
//    }
//
//
//    /* (non-Javadoc)
//     * @see org.mybatis.generator.api.dom.xml.Element#getFormattedContent(int)
//     */
//    public String getXmlElementFormattedContent(XmlElement xmlElement, int indentLevel) {
//        StringBuilder sb = new StringBuilder();
//
//        OutputUtilities.xmlIndent(sb, indentLevel);
//        sb.append('<');
//        sb.append(xmlElement.getName());
//
//        for (Attribute att : xmlElement.getAttributes()) {
//            sb.append(' ');
//            sb.append(att.getFormattedContent());
//        }
//
//        System.out.println("********************");
//        xmlElement.getAttributes().forEach(e -> System.out.print(e.getName() + " "));
//        System.out.println("********************");
//
//        if (xmlElement.getElements().size() > 0) {
//            sb.append(">"); //$NON-NLS-1$
//            for (Element element : xmlElement.getElements()) {
//                OutputUtilities.newLine(sb);
//                if (element instanceof XmlElement) {
//                    sb.append(getXmlElementFormattedContent((XmlElement) element, indentLevel + 1));
//                } else {
//                    sb.append(element.getFormattedContent(indentLevel + 1));
//                }
//            }
//            OutputUtilities.newLine(sb);
//            OutputUtilities.xmlIndent(sb, indentLevel);
//            sb.append("</"); //$NON-NLS-1$
//            sb.append(xmlElement.getName());
//            sb.append('>');
//
//        } else {
//            sb.append(" />"); //$NON-NLS-1$
//        }
//
//        System.out.println(sb.toString());
//        return sb.toString();
//    }
//}