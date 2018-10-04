-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email, pass)
VALUES (:id, :first_name, :last_name, :email, :pass)

-- :name update-user! :! :n
-- :doc updates an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieves a user record given the id
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc deletes a user record given the id
DELETE FROM users
WHERE id = :id


-- :name get-bodies :? :*
-- find all the bodies
SELECT * FROM bodies

-- :name add-body! :! :1
-- :doc adds a new body
INSERT INTO bodies
(name, volume, apoapsis, periapsis, mass, surface_area, satelites, circumference, form, orbital_period, parent)
VALUES (:name, :volume, :apoapsis, :periapsis, :mass, :surface_area, :satelites, :circumference, :form, :orbital_period, :parent);

--:name get-body :? :1
--:doc find a body by the name
SELECT * from bodies
WHERE name = :name;

