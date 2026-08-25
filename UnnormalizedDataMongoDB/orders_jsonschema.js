{
  "$jsonSchema": {
    "bsonType": "object",
    "required": ["_id", "order_date", "order_status", "order_total", "customer_snapshot", "line_items"],
    "properties": {
      "_id": { 
        "bsonType": "int", 
        "description": "Mapped directly from the CSV order_id" 
      },
      "order_date": { "bsonType": "string" },
      "order_status": { 
        "bsonType": "string", 
        "enum": ["SHIPPED", "CANCELLED"] 
      },
      "order_total": { 
        "bsonType": "decimal", 
        "description": "Computed sum of (quantity * unit_price) for all items. Must be Decimal128." 
      },
      "customer_snapshot": {
        "bsonType": "object",
        "required": ["customer_id", "name", "email", "shipping_state", "shipping_zip"],
        "properties": {
          "customer_id": { "bsonType": "string" },
          "name": { "bsonType": "string" },
          "email": { "bsonType": "string" },
          "shipping_state": { "bsonType": "string", "description": "Mapped from CSV customer_state" },
          "shipping_zip": { 
            "bsonType": "string",
            "pattern": "^[0-9]{5}$",
            "minLength": 5,
            "maxLength": 5,
            "description": "Must be exactly a 5-digit string. I couldn't find a native MongoDB padding operation, don't ignore this issue!"
          }
        }
      },
      "line_items": {
        "bsonType": "array",
        "minItems": 1,
        "items": {
          "bsonType": "object",
          "required": ["line_item_id", "product_id", "product_name", "category", "quantity", "unit_price", "supplier_snapshot"],
          "properties": {
            "line_item_id": { "bsonType": "int" },
            "product_id": { "bsonType": "string" },
            "product_name": { "bsonType": "string" },
            "category": { "bsonType": "string" },
            "quantity": { "bsonType": "int" },
            "unit_price": { "bsonType": "decimal", "description": "Must be Decimal128" },
            "supplier_snapshot": {
              "bsonType": "object",
              "required": ["supplier_id", "supplier_name", "supplier_state"],
              "properties": {
                "supplier_id": { "bsonType": "string" },
                "supplier_name": { "bsonType": "string" },
                "supplier_state": { "bsonType": "string" }
              }
            }
          }
        }
      }
    }
  }
}
