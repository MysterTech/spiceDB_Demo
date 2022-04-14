CREATE USER spicedbuser WITH PASSWORD 'spicedbpass' CREATEDB;
CREATE DATABASE spicedb
    WITH 
    OWNER = spicedbuser
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;