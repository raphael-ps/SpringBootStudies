CREATE TABLE CUSTOMER (
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(100)
);

CREATE TABLE PRODUCT (
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    DESCRIPTION VARCHAR(100),
    PRICE NUMERIC(20, 2)
);

CREATE TABLE SALES_ORDER(
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    TOTAL_AMOUNT NUMERIC(20, 2),
    CUSTOMER_ID INTEGER REFERENCES CUSTOMER(ID),
    CREATED_AT TIMESTAMP
);

CREATE TABLE ORDER_DETAILS(
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    ORDER_ID INTEGER REFERENCES SALES_ORDER(ID),
    PRODUCT_ID INTEGER REFERENCES PRODUCT(ID),
    QUANTITY INTEGER NOT NULL DEFAULT 0
);