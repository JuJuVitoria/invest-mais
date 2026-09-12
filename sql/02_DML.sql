
INSERT INTO ACCOUNT_TYPE (code, name, maxUsers)
VALUES (0, 'PERSONAL', 1);

INSERT INTO ACCOUNT_TYPE (code, name, maxUsers)
VALUES (1, 'BUSINESS', 100);

INSERT INTO ACCOUNT_STATUS (code, name)
VALUES (0, 'ACTIVE');

INSERT INTO ACCOUNT_STATUS (code, name)
VALUES (1, 'BLOCKED');

INSERT INTO ACCOUNT_STATUS (code, name)
VALUES (2, 'PENDING');

INSERT INTO WALLET_CATEGORY (categoryID, name)
VALUES (1, 'CDI/CDB');

INSERT INTO WALLET_CATEGORY (categoryID, name)
VALUES (2, 'Fundos');

INSERT INTO WALLET_CATEGORY (categoryID, name)
VALUES (3, 'Criptomoedas');

INSERT INTO WALLET_CATEGORY (categoryID, name)
VALUES (4, 'Ações');

-- Conta e usuário de teste
INSERT INTO ACCOUNT (accountID, idPrimaryOwner, accountTypeCode, accountStatusCode)
VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', NULL, 0, 0);

INSERT INTO "USER" (userID, accountID, name, email, password)
VALUES ('11111111-1111-1111-1111-111111111111',
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
        'Rafael Teste',
        'rafael.teste@investmais.com',
        '123456');

UPDATE ACCOUNT
   SET idPrimaryOwner = '11111111-1111-1111-1111-111111111111'
 WHERE accountID = 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa';

-- Carteiras
INSERT INTO WALLET (walletID, CategoryID, userID, name, totalBalance)
VALUES ('21111111-1111-1111-1111-111111111111', 1,
        '11111111-1111-1111-1111-111111111111',
        'CDI/CDB', 25000);

INSERT INTO WALLET (walletID, CategoryID, userID, name, totalBalance)
VALUES ('31111111-1111-1111-1111-111111111111', 2,
        '11111111-1111-1111-1111-111111111111',
        'Fundos', 10000);

INSERT INTO WALLET (walletID, CategoryID, userID, name, totalBalance)
VALUES ('41111111-1111-1111-1111-111111111111', 3,
        '11111111-1111-1111-1111-111111111111',
        'Criptomoedas', 9000);

-- Ativos
INSERT INTO ASSET (assetID, assetName, assetTicker, currentValue, assetType)
VALUES ('51111111-1111-1111-1111-111111111111',
        'Bitcoin', 'BTC', 75949.69, 'CRYPTO');

INSERT INTO CRYPTO_ASSET (assetID, blockchain)
VALUES ('51111111-1111-1111-1111-111111111111', 'Bitcoin');

INSERT INTO ASSET (assetID, assetName, assetTicker, currentValue, assetType)
VALUES ('61111111-1111-1111-1111-111111111111',
        'Vale', 'VALE3', 89.75, 'STOCK');

INSERT INTO STOCK_ASSET (assetID)
VALUES ('61111111-1111-1111-1111-111111111111');

INSERT INTO ASSET (assetID, assetName, assetTicker, currentValue, assetType)
VALUES ('71111111-1111-1111-1111-111111111111',
        'VGRI11', 'VGRI11', 6.74, 'REAL_ESTATE');

INSERT INTO REAL_ESTATE_FUNDS_ASSET (assetID)
VALUES ('71111111-1111-1111-1111-111111111111');

INSERT INTO WALLET_ASSET (walletID, assetID, quantity)
VALUES ('41111111-1111-1111-1111-111111111111',
        '51111111-1111-1111-1111-111111111111',
        0.15);

-- ============================================================
-- SELECT
-- ============================================================

SELECT * FROM ACCOUNT_TYPE;
SELECT * FROM ACCOUNT_STATUS;
SELECT * FROM WALLET_CATEGORY;
SELECT * FROM "USER";
SELECT * FROM WALLET;
SELECT * FROM ASSET;
SELECT * FROM WALLET_ASSET;

SELECT w.walletID,
       w.name AS wallet_name,
       w.totalBalance,
       u.name AS user_name
  FROM WALLET w
  JOIN "USER" u ON u.userID = w.userID
 ORDER BY w.name;

-- ============================================================
-- UPDATE
-- ============================================================

UPDATE WALLET
   SET name = 'Carteira CDI/CDB Atualizada',
       totalBalance = 30000
 WHERE walletID = '21111111-1111-1111-1111-111111111111';

-- Conferência do UPDATE
SELECT walletID, CategoryID, userID, name, totalBalance
  FROM WALLET
 WHERE walletID = '21111111-1111-1111-1111-111111111111';

-- ============================================================
-- DELETE
-- ============================================================

-- Exclusão de uma carteira sem ativos vinculados.
INSERT INTO WALLET (walletID, CategoryID, userID, name, totalBalance)
VALUES ('81111111-1111-1111-1111-111111111111', 4,
        '11111111-1111-1111-1111-111111111111',
        'Carteira Temporária', 5000);

DELETE FROM WALLET
 WHERE walletID = '81111111-1111-1111-1111-111111111111';

-- Conferência do DELETE
SELECT *
  FROM WALLET
 WHERE walletID = '81111111-1111-1111-1111-111111111111';

COMMIT;
