create table if not exists travel_charge_order
(
    id            LONG not null,
    company_id    character varying(16),
    amount        character varying(16),
    charge_status character varying(16),
    primary key (id)
);
