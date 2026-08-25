{
  "$jsonSchema": {
    "bsonType": "object",
    "required": ["_id", "name", "email", "state", "zip"],
    "properties": {
      "_id": { 
        "bsonType": "string", 
        "description": "Mapped from CSV customer_id (e.g., C100004)" 
      },
      "name": { "bsonType": "string" },
      "email": { "bsonType": "string" },
      "state": { "bsonType": "string" },
      "zip": { 
        "bsonType": "string",
        "pattern": "^[0-9]{5}$",
        "minLength": 5,
        "maxLength": 5,
        "description": "Must be exactly a 5-digit string. I couldn't find a native MongoDB padding operation, don't ignore this issue!"
      }
    }
  }
}
