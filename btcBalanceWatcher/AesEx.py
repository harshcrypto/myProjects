# encrypt & decrypt a text using AES
from Crypto.Cipher import AES
obj = AES.new('1234567812345678', AES.MODE_CBC, 'This is an IV456')
message = "The answer is no nsks dsidjsd jskdjs dsd12345678"
ciphertext = obj.encrypt(message)
print(ciphertext)
obj2 = AES.new('1234567812345678', AES.MODE_CBC, 'This is an IV456')
message = obj2.decrypt(ciphertext)
print(message)



