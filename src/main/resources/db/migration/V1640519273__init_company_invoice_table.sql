create table if not exists company_invoice
(
    id                 LONG not null,
    order_id           LONG not null,
    company_id         character varying(16),
    payer_name         character varying(128),
    payer_register_no  character varying(32),
    payer_address      character varying(128),
    payer_telephone    character varying(16),
    payer_bank_name    character varying(32),
    payer_bank_account character varying(16),
    user_email         character varying(64),
    user_mobile_no     character varying(11),
    amount             character varying(16),
    merchant_name      character varying(128),
    status             character varying(16),
    primary key (id)
);
