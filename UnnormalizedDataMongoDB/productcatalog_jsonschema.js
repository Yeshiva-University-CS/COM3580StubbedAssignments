{
  "$jsonSchema": {
    "bsonType": "object",
    "required": ["_id", "name", "category", "current_unit_price", "primary_supplier"],
    "properties": {
      "_id": { 
        "bsonType": "string", 
        "description": "Mapped from CSV product_id (e.g., P54)" 
      },
      "name": { "bsonType": "string" },
      "category": { "bsonType": "string" },
      "current_unit_price": { "bsonType": "decimal", "description": "Must be Decimal128" },
      "primary_supplier": {
        "bsonType": "object",
        "required": ["supplier_id", "name", "state"],
        "properties": {
          "supplier_id": { "bsonType": "string" },
          "name": { "bsonType": "string" },
          "state": { "bsonType": "string" }
        }
      }
    }
  }
}
