# packaging-testcase

Very simple REST API for managing packages

# Entities

- MAIL
  Represents different types of mail
  > id
  > type (letter, package, parcel, postcard)
  > recipient index
  > recipient address
  > recipient name

- MAIL STATE
  Represents mail state
  > id
  > mail
  > mail state type (registered, arrived, departed, recieved)
  > created at
  > post

- POST
  Represents mail post
  > id
  > name
  > address

# Available operations

- Register mail (Response: created entity):

  POST /api/mail
  {
    "mail_type": "letter",
    "recipient_index": 111222333,
    "recipient_address": "address",
    "recipient_name": "joe"
  }

- Fetch mail history by id, i.e. all mail states associated with id (Response: list of MailStateDto):
 
    GET /api/mail/history/{mail_id}

- Change mail state (Response: created MailStateDto)
 
    POST /api/mail/{mail_id}

- Get current state of mail by id (Response: most recent MailStateDto)

  GET /api/mail/status/{mail_id} 
