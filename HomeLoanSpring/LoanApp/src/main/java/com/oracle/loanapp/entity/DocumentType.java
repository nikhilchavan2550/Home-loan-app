//package com.oracle.loanapp.entity;
//
//public enum DocumentType {
//    PAN_CARD("Pan Card"),
//    VOTER_ID("Voter id"),
//    SALARY_SLIP("Salary Slip"),
//    LOA("LOA"),
//    NOC_FROM_BUILDER("NOC from Builder"),
//    AGREEMENT_TO_SALE("Agreement to Sale"),
//
//    // Legacy values to support old data
//    AADHAAR("Aadhaar Card"),
//    PAN("Pan Card"),
//    INCOME_PROOF("Income Proof"),
//    PROPERTY_PROOF("Property Proof"),
//    PHOTO("Photo");
//
//    private final String displayName;
//
//    DocumentType(String displayName) {
//        this.displayName = displayName;
//    }
//
//    public String getDisplayName() {
//        return displayName;
//    }
//
//    public static DocumentType fromDisplayName(String name) {
//        if (name == null) {
//            return null;
//        }
//        for (DocumentType type : values()) {
//            // Check both display name and the enum's constant name
//            if (type.getDisplayName().equalsIgnoreCase(name.trim()) || type.name().equalsIgnoreCase(name.trim())) {
//                return type;
//            }
//        }
//        throw new IllegalArgumentException("Invalid document type: " + name);
//    }
//}
