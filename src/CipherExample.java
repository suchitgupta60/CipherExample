import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  

import javax.crypto.Cipher;  
import javax.crypto.CipherInputStream;  
import javax.crypto.CipherOutputStream;  
import javax.crypto.SecretKey;  
import javax.crypto.SecretKeyFactory;  
import javax.crypto.spec.DESKeySpec;  

public class CipherExample {  

	public static void main(String[] args) {  
		long startTime = System.nanoTime(); //start timer  
		try   
		{  

			String key = "A"; // needs to be at least 8 characters for DES  

			for (int i = 0; i < 26; i++) //for loop to find characters in string  
			{  
				String myKey = key + (char)(i + 65); //finds additional character for key  

				for (int j = 0; j < 26; j++){
					String tkey = myKey + (char)(j+65);

					for(int k =0; k < 26; k++){
						String pkey = tkey + (char)(k+65);

						for (int l = 0; l < 26; l++){
							String qkey = pkey + (char)(+65);

							for(int m =0; m < 26; m++){
								String rkey = qkey + (char)(m+65);

								for (int n = 0; n < 1; n++){
									String skey = rkey + (char)(n+65);
									for (int o = 0; o < 1; o++){
										String ukey = skey + (char)(o+65);
										 
										FileInputStream fis = new FileInputStream("/Users/SUCHIT/Documents/workspace/CipherExample/src/original.txt");  
										FileOutputStream fos = new FileOutputStream("/Users/SUCHIT/Documents/workspace/CipherExample/src/encrypted.txt");   
										encrypt(ukey, fis, fos);   

										FileInputStream fis2 = new FileInputStream("/Users/SUCHIT/Documents/workspace/CipherExample/src/encrypted.txt");  
										FileOutputStream fos2 = new FileOutputStream("/Users/SUCHIT/Documents/workspace/CipherExample/src/decrypted.txt");  
										decrypt(ukey, fis2, fos2); 

										System.out.println(ukey); 
									
									}
								}
							}
						}
					}
				}
			}  
		}   
		catch (Throwable e)   
		{  
			e.printStackTrace();  
		}  
		long endTime = System.nanoTime(); //end time  
		System.out.println("It took: " + (endTime - startTime) + " nanoseconds to run"); //display time  
	}  

	public static void encrypt(String myKey, InputStream is, OutputStream os) throws Throwable //changed key to myKey, os to es  
	{  
		encryptOrDecrypt(myKey, Cipher.ENCRYPT_MODE, is, os);//changed key to myKey and os to es  
	}  

	public static void decrypt(String myKey, InputStream is, OutputStream os) throws Throwable //changed key to myKey and OutputStream os to InputStream es  
	{  
		encryptOrDecrypt(myKey, Cipher.DECRYPT_MODE, is, os);//change key to myKey and os to es  
	}  

	public static void encryptOrDecrypt(String key, int mode, InputStream is, OutputStream os) throws Throwable   
	{//changed OutputStream os to InputStream es  

		DESKeySpec dks = new DESKeySpec(key.getBytes());  
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");  
		SecretKey desKey = skf.generateSecret(dks);  
		Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE  

		if (mode == Cipher.ENCRYPT_MODE)   
		{  
			cipher.init(Cipher.ENCRYPT_MODE, desKey);  
			CipherInputStream cis = new CipherInputStream(is, cipher);  
			doCopy(cis, os);  
		}   
		else if (mode == Cipher.DECRYPT_MODE)   
		{  
			cipher.init(Cipher.DECRYPT_MODE, desKey);  
			CipherOutputStream cos = new CipherOutputStream(os, cipher);  
			doCopy(is, cos);  
		}  
	}  

	public static void doCopy(InputStream is, OutputStream os) throws IOException   
	{  
		byte[] bytes = new byte[64];  
		int numBytes;  
		while ((numBytes = is.read(bytes)) != -1)   
		{  
			os.write(bytes, 0, numBytes);  
		}  
		//os.flush();  
		os.close();  
		is.close();  
	}  

}  