CREATE DATABASE products;
USE products;

-- product type
CREATE TABLE product_types (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name NVARCHAR(500) NOT NULL UNIQUE,
    description TEXT,
    icon NVARCHAR(255),
    image NVARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- brand
CREATE TABLE brands (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name NVARCHAR(500) NOT NULL UNIQUE,
    image NVARCHAR(255),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- tag
CREATE TABLE tags (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name NVARCHAR(500) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- attribute types
CREATE TABLE attribute_types (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name NVARCHAR(500) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- attributes
CREATE TABLE attributes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name NVARCHAR(500) NOT NULL UNIQUE,
    description TEXT,
    type_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (type_id) REFERENCES attribute_types(id) ON DELETE CASCADE
);

-- attribute values
CREATE TABLE attribute_values (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name NVARCHAR(500) NOT NULL UNIQUE,
    description TEXT,
    attribute_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (attribute_id) REFERENCES attributes(id) ON DELETE CASCADE
);

-- promotion types
CREATE TABLE promotion_types (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name NVARCHAR(500) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- promotions
CREATE TABLE promotions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name NVARCHAR(500) NOT NULL,
    description TEXT,
    discount_percent DECIMAL(5, 2) DEFAULT 0,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    type_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (type_id) REFERENCES promotion_types(id) ON DELETE CASCADE
);

-- products
CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name NVARCHAR(500) NOT NULL UNIQUE,
    description TEXT,
    base_price DECIMAL(10, 2) NOT NULL, -- Giá gốc của sản phẩm
    type_id bigint not null,
    brand_id bigint not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    Foreign key (type_id) references product_types(id) on delete cascade,
    Foreign key (brand_id) references brands(id) on delete cascade
);

-- product versions
CREATE TABLE product_versions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    version_name NVARCHAR(500) NOT NULL,   -- Tên phiên bản sản phẩm
    price DECIMAL(10, 2) NOT NULL,         -- Giá phiên bản
    stock_quantity INT DEFAULT 0,          -- Số lượng tồn kho cho phiên bản này
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_main BOOLEAN DEFAULT FALSE,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- product promotion
CREATE TABLE product_promotions (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    promotion_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (promotion_id) REFERENCES promotions(id) ON DELETE CASCADE
);

-- product tags (many-to-many relationship)
CREATE TABLE product_tags (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);

-- product attributes 
CREATE TABLE product_attributes (
    product_version_id BIGINT NOT NULL,
    attribute_value_id BIGINT NOT NULL,
    PRIMARY KEY (product_version_id, attribute_value_id),
    FOREIGN KEY (product_version_id) REFERENCES product_versions(id) ON DELETE CASCADE,
    FOREIGN KEY (attribute_value_id) REFERENCES attribute_values(id) ON DELETE CASCADE
);

-- product images
CREATE TABLE product_images (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_version_id BIGINT NOT NULL,
    image NVARCHAR(255) NOT NULL,
    is_main BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_version_id) REFERENCES product_versions(id) ON DELETE CASCADE
);

-- suppliers
CREATE TABLE suppliers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name NVARCHAR(255) NOT NULL UNIQUE,
    phone NVARCHAR(11),
    email NVARCHAR(255),
    address NVARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- import receipts
CREATE TABLE import_receipts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    supplier_id BIGINT NOT NULL,
    receipt_date DATETIME NOT NULL,
    total_amount DECIMAL(15, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE CASCADE
);

-- import receipt details
CREATE TABLE import_receipt_details (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    receipt_id BIGINT NOT NULL,
    product_version_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(15, 2) NOT NULL,
    total_price DECIMAL(15, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (receipt_id) REFERENCES import_receipts(id) ON DELETE CASCADE,
    FOREIGN KEY (product_version_id) REFERENCES product_versions(id) ON DELETE CASCADE
);

-- vouchers	
CREATE TABLE vouchers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code NVARCHAR(50) NOT NULL UNIQUE,
    name NVARCHAR(255) NOT NULL,
    description TEXT,
    discount_percent DECIMAL(5, 2),
    discount_amount DECIMAL(10, 2),
    min_order_value DECIMAL(10, 2),
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- product vouchers
CREATE TABLE product_vouchers (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_version_id BIGINT NOT NULL,
    voucher_id BIGINT NOT NULL,
    FOREIGN KEY (product_version_id) REFERENCES product_versions(id) ON DELETE CASCADE,
    FOREIGN KEY (voucher_id) REFERENCES vouchers(id) ON DELETE CASCADE
);

-- invoices
CREATE TABLE invoices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    invoice_date DATETIME NOT NULL,
    total_amount DECIMAL(15, 2) NOT NULL,
    discount DECIMAL(15, 2) DEFAULT 0,
    final_amount DECIMAL(15, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- invoice details
CREATE TABLE invoice_details (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    invoice_id BIGINT NOT NULL,
    product_version_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(15, 2) NOT NULL,
    discount DECIMAL(15, 2) DEFAULT 0,
    total_price DECIMAL(15, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (invoice_id) REFERENCES invoices(id) ON DELETE CASCADE,
    FOREIGN KEY (product_version_id) REFERENCES product_versions(id) ON DELETE CASCADE
);

-- invoice vouchers
CREATE TABLE invoice_vouchers (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    invoice_id BIGINT NOT NULL,
    voucher_id BIGINT NOT NULL,
    FOREIGN KEY (invoice_id) REFERENCES invoices(id) ON DELETE CASCADE,
    FOREIGN KEY (voucher_id) REFERENCES vouchers(id) ON DELETE CASCADE
);

-- shipping costs
CREATE TABLE shipping_costs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    region NVARCHAR(255) NOT NULL,
    cost DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- stored procedures for managing imports and invoicing
DELIMITER $$

CREATE PROCEDURE add_import_receipt(
    IN supplierId BIGINT,
    IN receiptDate DATETIME,
    IN productDetails JSON
)
BEGIN
    DECLARE receiptId BIGINT;
    DECLARE totalAmount DECIMAL(15, 2) DEFAULT 0;
    DECLARE productVersionId BIGINT;
    DECLARE quantity INT;
    DECLARE unitPrice DECIMAL(15, 2);
    DECLARE totalPrice DECIMAL(15, 2);
    DECLARE i INT DEFAULT 0;
    DECLARE maxIndex INT;

    SET maxIndex = JSON_LENGTH(productDetails) - 1;

    INSERT INTO import_receipts (supplier_id, receipt_date, total_amount)
    VALUES (supplierId, receiptDate, 0);
    SET receiptId = LAST_INSERT_ID();

    WHILE i <= maxIndex DO
        SET productVersionId = JSON_EXTRACT(productDetails, CONCAT('$[', i, '].product_version_id'));
        SET quantity = JSON_EXTRACT(productDetails, CONCAT('$[', i, '].quantity'));
        SET unitPrice = JSON_EXTRACT(productDetails, CONCAT('$[', i, '].unit_price'));
        SET totalPrice = quantity * unitPrice;

        INSERT INTO import_receipt_details (receipt_id, product_version_id, quantity, unit_price, total_price)
        VALUES (receiptId, productVersionId, quantity, unitPrice, totalPrice);

        SET totalAmount = totalAmount + totalPrice;
        SET i = i + 1;
    END WHILE;

    UPDATE import_receipts SET total_amount = totalAmount WHERE id = receiptId;
END $$

DELIMITER ;

DELIMITER $$

DELIMITER $$

CREATE PROCEDURE add_invoice(
    IN userId BIGINT,
    IN invoiceDate DATETIME,
    IN productDetails JSON,  -- chi tiết sản phẩm bao gồm thông tin về sản phẩm, số lượng
    IN voucherCode NVARCHAR(50), -- mã voucher (nếu có)
    IN shippingArea NVARCHAR(100) -- khu vực giao hàng để tính phí ship
)
BEGIN
    DECLARE invoiceId BIGINT;
    DECLARE totalAmount DECIMAL(15, 2) DEFAULT 0;
    DECLARE totalDiscount DECIMAL(15, 2) DEFAULT 0;
    DECLARE finalAmount DECIMAL(15, 2) DEFAULT 0;
    DECLARE productVersionId BIGINT;
    DECLARE quantity INT;
    DECLARE unitPrice DECIMAL(15, 2);
    DECLARE discount DECIMAL(15, 2) DEFAULT 0;
    DECLARE totalPrice DECIMAL(15, 2);
    DECLARE promotionDiscount DECIMAL(15, 2);
    DECLARE i INT DEFAULT 0;
    DECLARE maxIndex INT;
    DECLARE voucherDiscount DECIMAL(15, 2) DEFAULT 0;
    DECLARE voucherDiscountAmount DECIMAL(15, 2) DEFAULT 0;
    DECLARE voucherValid BOOLEAN DEFAULT FALSE;
    DECLARE promotionId BIGINT;
    DECLARE promotionValid BOOLEAN DEFAULT FALSE;
    DECLARE startDate DATETIME;
    DECLARE endDate DATETIME;
    DECLARE shippingFee DECIMAL(15, 2) DEFAULT 0;

    -- Lấy thông tin voucher nếu có
    IF voucherCode IS NOT NULL THEN
        SELECT discount_percent, discount_amount, start_date, end_date
        INTO voucherDiscount, voucherDiscountAmount, startDate, endDate
        FROM vouchers
        WHERE code = voucherCode AND CURDATE() BETWEEN startDate AND endDate;
        
        -- Kiểm tra nếu voucher hợp lệ
        IF voucherDiscount IS NOT NULL THEN
            SET voucherValid = TRUE;
        END IF;
    END IF;

    -- Tính toán tổng giá trị của hóa đơn và áp dụng khuyến mãi cho từng sản phẩm
    SET maxIndex = JSON_LENGTH(productDetails) - 1;

    INSERT INTO invoices (user_id, invoice_date, total_amount, discount, final_amount)
    VALUES (userId, invoiceDate, 0, 0, 0);
    SET invoiceId = LAST_INSERT_ID();

    WHILE i <= maxIndex DO
        -- Lấy thông tin sản phẩm từ chi tiết JSON
        SET productVersionId = JSON_EXTRACT(productDetails, CONCAT('$[', i, '].product_version_id'));
        SET quantity = JSON_EXTRACT(productDetails, CONCAT('$[', i, '].quantity'));

        -- Lấy giá và tính giá trị của sản phẩm
        SELECT price INTO unitPrice FROM product_versions WHERE id = productVersionId;

        -- Tính giá trị sản phẩm
        SET totalPrice = quantity * unitPrice;

        -- Kiểm tra và áp dụng khuyến mãi cho từng sản phẩm
        SELECT p.id, p.discount_percent, p.start_date, p.end_date
        INTO promotionId, promotionDiscount, startDate, endDate
        FROM promotions p
        JOIN product_promotions pp ON p.id = pp.promotion_id
        WHERE pp.product_version_id = productVersionId AND CURDATE() BETWEEN startDate AND endDate;

        IF promotionDiscount IS NOT NULL THEN
            SET promotionValid = TRUE;
            SET totalPrice = totalPrice - (totalPrice * promotionDiscount / 100);
        ELSE
            SET promotionValid = FALSE;
        END IF;

        -- Cập nhật giá trị sản phẩm sau khi áp dụng khuyến mãi
        SET discount = (totalPrice - quantity * unitPrice);
        SET totalAmount = totalAmount + totalPrice;

        INSERT INTO invoice_details (invoice_id, product_version_id, quantity, unit_price, discount, total_price)
        VALUES (invoiceId, productVersionId, quantity, unitPrice, discount, totalPrice);

        SET i = i + 1;
    END WHILE;

    -- Tính toán tổng giá trị hóa đơn sau khuyến mãi
    SET finalAmount = totalAmount;

    -- Nếu voucher hợp lệ, áp dụng voucher vào tổng tiền hóa đơn
    IF voucherValid THEN
        -- Tính toán discount của voucher
        IF voucherDiscountAmount > 0 THEN
            SET finalAmount = finalAmount - voucherDiscountAmount;
        ELSE
            SET finalAmount = finalAmount - (finalAmount * voucherDiscount / 100);
        END IF;
    END IF;

    -- Tính phí ship dựa trên khu vực giao hàng
    SELECT fee_amount INTO shippingFee
    FROM shipping_fees
    WHERE area = shippingArea;

    -- Thêm phí ship vào tổng tiền hóa đơn
    SET finalAmount = finalAmount + shippingFee;

    -- Cập nhật hóa đơn với tổng tiền sau khi áp dụng khuyến mãi, voucher và phí ship
    UPDATE invoices SET total_amount = totalAmount, discount = totalAmount - finalAmount, final_amount = finalAmount WHERE id = invoiceId;

    -- Nếu voucher hợp lệ, lưu vào bảng invoice_vouchers
    IF voucherValid THEN
        INSERT INTO invoice_vouchers (invoice_id, voucher_id)
        SELECT invoiceId, v.id
        FROM vouchers v
        WHERE v.code = voucherCode;
    END IF;

END $$

DELIMITER ;


