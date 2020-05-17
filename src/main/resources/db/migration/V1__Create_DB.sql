create table if not exists providers
(
    prov_id serial not null
        constraint provider_pkey
            primary key,
    name text not null,
    date_begin date not null,
    date_end date
);

-- -- alter table providers owner to "lda-dima";

create table if not exists animals
(
    id serial not null
        constraint animals_pkey
            primary key,
    name text not null,
    kind_animal text not null,
    type text not null,
    climatic_habitat text not null,
    gender text not null,
    physical_state text not null,
    progeny integer not null,
    birthday date not null,
    departure_date date,
    departure_reason text,
    need_relocation boolean not null
);

-- -- alter table animals owner to "lda-dima";

create table if not exists zoos
(
    id serial not null
        constraint zoos_pkey
            primary key,
    name text not null
);

-- -- alter table zoos owner to "lda-dima";

create table if not exists feeds
(
    id serial not null
        constraint feeds_pkey
            primary key,
    name text not null,
    type text not null,
    stock integer not null,
    volume_independent_production integer not null
);

-- -- alter table feeds owner to "lda-dima";

create table if not exists employees
(
    id serial not null
        constraint employees_pkey
            primary key,
    surname text not null,
    name text not null,
    patronymic text not null,
    category text not null,
    duration_work integer not null,
    age integer not null,
    gender text not null,
    salary numeric not null
);

-- alter table employees owner to "lda-dima";

create unique index if not exists employees_surname_name_patronymic_category_uindex
    on employees (surname, name, patronymic, category);

create table if not exists med_card
(
    animal_id integer not null
        constraint med_card_pk
            primary key
        constraint med_card_animal_id_fkey
            references animals
            on update cascade on delete cascade,
    height integer not null,
    weight integer not null,
    development text not null,
    need_hospital boolean not null,
    date_last_inspection date not null
);

-- alter table med_card owner to "lda-dima";

create table if not exists providers_specialization
(
    feed_id integer not null
        constraint specialization_feed_id_fkey
            references feeds
            on update cascade on delete cascade,
    provider_id integer not null
        constraint specialization_provider_id_fkey
            references providers
            on update cascade on delete cascade,
    constraint providers_specialization_pk
        primary key (feed_id, provider_id)
);

-- alter table providers_specialization owner to "lda-dima";

create unique index if not exists providers_specialization_feed_id_provider_id_uindex
    on providers_specialization (feed_id, provider_id);

create table if not exists animal_compatibility
(
    animal_id integer not null
        constraint animal_compatibility_animal_id_fkey
            references animals
            on update cascade on delete cascade,
    animal_kind text not null,
    constraint animal_compatibility_pk
        primary key (animal_id, animal_kind)
);

-- alter table animal_compatibility owner to "lda-dima";

create unique index if not exists animal_compatibility_animal_id_animal_kind_idx
    on animal_compatibility (animal_id, animal_kind);

create table if not exists access_animals
(
    employer_id integer not null
        constraint access_animals_employer_id_fkey
            references employees
            on update cascade on delete cascade,
    animal_id integer not null
        constraint access_animals_animal_id_fkey
            references animals
            on update cascade on delete cascade,
    constraint access_animals_pk
        primary key (employer_id, animal_id)
);

-- alter table access_animals owner to "lda-dima";

create unique index if not exists access_animals_employer_id_animal_id_idx
    on access_animals (employer_id, animal_id);

create table if not exists responsible_animals
(
    employer_id integer not null
        constraint responsible_animals_employer_id_fkey
            references employees
            on update cascade on delete cascade,
    animal_id integer not null
        constraint responsible_animals_animal_id_fkey
            references animals
            on update cascade on delete cascade,
    date_begin date not null,
    date_end date,
    constraint responsible_animals_pk
        primary key (employer_id, animal_id)
);

-- alter table responsible_animals owner to "lda-dima";

create unique index if not exists responsible_animals_employer_id_animal_id_idx
    on responsible_animals (employer_id, animal_id);

create table if not exists illnesses
(
    id serial not null
        constraint illnesses_pkey
            primary key,
    name text not null
);

-- alter table illnesses owner to "lda-dima";

create table if not exists illness_animals
(
    animal_id integer not null
        constraint illness_animals_animal_id_fkey
            references animals
            on update cascade on delete cascade,
    illness_id integer not null
        constraint illness_animals_illness_id_fkey
            references illnesses
            on update cascade on delete cascade,
    date_begin date not null,
    date_end date,
    constraint illness_animals_pk
        primary key (animal_id, illness_id)
);

-- alter table illness_animals owner to "lda-dima";

create unique index if not exists illness_animals_animal_id_illness_id_idx
    on illness_animals (animal_id, illness_id);

create table if not exists cells_animals
(
    cell_id integer not null,
    animal_id integer not null
        constraint cells_animals_animal_id_fkey
            references animals
            on update cascade on delete cascade,
    date_begin date not null,
    date_end date,
    heating boolean not null,
    constraint cells_animals_pk
        primary key (cell_id, animal_id)
);

-- alter table cells_animals owner to "lda-dima";

create unique index if not exists cells_animals_animal_id_cell_id_idx
    on cells_animals (animal_id, cell_id);

create table if not exists animal_receipt
(
    zoo_id integer not null
        constraint animal_receipt_zoo_id_fkey
            references zoos
            on update cascade on delete cascade,
    animal_id integer not null
        constraint animal_receipt_animal_id_fkey
            references animals
            on update cascade on delete cascade,
    date_receipt date not null,
    constraint animal_receipt_pk
        primary key (zoo_id, animal_id)
);

-- alter table animal_receipt owner to "lda-dima";

create unique index if not exists animal_receipt_animal_id_zoo_id_idx
    on animal_receipt (animal_id, zoo_id);

create table if not exists supply
(
    id serial not null
        constraint supply_pkey
            primary key,
    provider_id integer not null
        constraint supply_provider_id_fkey
            references providers
            on update cascade on delete cascade,
    feed_id integer not null
        constraint supply_feed_id_fkey
            references feeds
            on update cascade on delete cascade,
    price numeric not null,
    feed_amount integer not null,
    date_supply date not null
);

-- alter table supply owner to "lda-dima";

create table if not exists vaccines
(
    animal_id integer not null
        constraint vaccines_animal_id_fkey
            references animals
            on update cascade on delete cascade,
    vaccine_id integer not null,
    medicine_name text not null,
    dose integer not null,
    date_vaccine date not null,
    constraint vaccines_pk
        primary key (animal_id, vaccine_id)
);

-- alter table vaccines owner to "lda-dima";

create unique index if not exists vaccines_vaccine_id_animal_id_idx
    on vaccines (vaccine_id, animal_id);

create table if not exists odd_day_ration
(
    animal_id integer not null
        constraint odd_day_ration_animal_id_fkey
            references animals
            on update cascade on delete cascade,
    season text not null,
    feed_id integer not null
        constraint odd_day_ration_feed_id_fkey
            references feeds
            on update cascade on delete cascade,
    quantity integer not null,
    constraint odd_day_ration_pk
        primary key (animal_id, season)
);

-- alter table odd_day_ration owner to "lda-dima";

create table if not exists even_day_ration
(
    animal_id integer not null
        constraint even_day_ration_animal_id_fkey
            references animals
            on update cascade on delete cascade,
    season text not null,
    feed_id integer not null
        constraint even_day_ration_feed_id_fkey
            references feeds
            on update cascade on delete cascade,
    quantity integer not null,
    constraint even_day_ration_pk
        primary key (animal_id, season)
);

-- alter table even_day_ration owner to "lda-dima";

create table if not exists productive_age
(
    kind text not null
        constraint table_name_pk
            primary key,
    age integer not null
);

-- alter table productive_age owner to "lda-dima";

