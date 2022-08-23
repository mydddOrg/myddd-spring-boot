create table oauth_client_details (
    client_id VARCHAR(256) PRIMARY KEY,
    resource_ids VARCHAR(256),
    client_secret VARCHAR(256),
--    "read,write"
    scope VARCHAR(256),
--    "password,authorization_code,refresh_token"
    authorized_grant_types VARCHAR(256),
--    "https://examle.com/callbck"
    web_server_redirect_uri VARCHAR(256),
--
    authorities VARCHAR(256),
--    36000
    access_token_validity INTEGER,
--    36000
    refresh_token_validity INTEGER,
--
    additional_information VARCHAR(4096),
--    true
    autoapprove VARCHAR(256)
);