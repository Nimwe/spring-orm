CREATE OR REPLACE PROCEDURE create_accounts()
LANGUAGE plpgsql
AS $$
BEGIN
    FOR i IN 1..200 LOOP
        INSERT INTO public.account (creationtime, balance, active, user_id)
        VALUES (
            current_timestamp,
            1000 * i,
            true,
            (SELECT id FROM public."user" OFFSET floor(random() * (SELECT COUNT(*) FROM public."user")) LIMIT 1)
        );
    END LOOP;
END;
$$;

CALL create_accounts();