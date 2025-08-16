//package com.oracle.loanapp.entity;
//
//import jakarta.persistence.AttributeConverter;
//import jakarta.persistence.Converter;
//
//@Converter(autoApply = true)
//public class DocumentTypeConverter implements AttributeConverter<DocumentType, String> {
//
//    @Override
//    public String convertToDatabaseColumn(DocumentType attribute) {
//        if (attribute == null) {
//            return null;
//        }
//        return attribute.getDisplayName();
//    }
//
//    @Override
//    public DocumentType convertToEntityAttribute(String dbData) {
//        if (dbData == null) {
//            return null;
//        }
//        return DocumentType.fromDisplayName(dbData);
//    }
//}
