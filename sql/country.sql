/**
 * Country
 */
create table country (
  id integer primary key autoincrement,
  country_name text,
  country_code text,
  city_id integer
);
