第1关：基本测试
根据S-DES算法编写和调试程序，提供GUI解密支持用户交互。输入可以是8bit的数据和10bit的密钥，输出是8bit的密文。

第2关：交叉测试
考虑到是算法标准，所有人在编写程序的时候需要使用相同算法流程和转换单元(P-Box、S-Box等)，以保证算法和程序在异构的系统或平台上都可以正常运行。设有A和B两组位同学(选择相同的密钥K)；则A、B组同学编写的程序对明文P进行加密得到相同的密文C；或者B组同学接收到A组程序加密的密文C，使用B组程序进行解密可得到与A相同的P。

第3关：扩展功能
考虑到向实用性扩展，加密算法的数据输入可以是ASII编码字符串(分组为1 Byte)，对应地输出也可以是ACII字符串(很可能是乱码)。

第4关：暴力破解
假设你找到了使用相同密钥的明、密文对(一个或多个)，请尝试使用暴力破解的方法找到正确的密钥Key。在编写程序时，你也可以考虑使用多线程的方式提升破解的效率。请设定时间戳，用视频或动图展示你在多长时间内完成了暴力破解。

第5关：封闭测试
根据第4关的结果，进一步分析，对于你随机选择的一个明密文对，是不是有不止一个密钥Key？进一步扩展，对应明文空间任意给定的明文分组P_{n}，是否会出现选择不同的密钥K_{i}\ne K_{j}加密得到相同密文C_n的情况？

1.UI设计
如下图所示，在此界面中我们可以输入数据与密钥进行加密、解密，以此来直观得到加密与解密的结果。

2.基础加解密算法
测试案例：使用密钥0011110110对明文011000110111100101101000(ASCIIchenyouhao)进行加密，并通过逆向操作验证解密是否成功：

![ascll加密（二进制输出）](https://github.com/user-attachments/assets/29283839-9cad-4869-8c0a-f1185adbda2d)
![二进制加密（输出）](https://github.com/user-attachments/assets/8e43bd70-fd5c-4a72-bc60-0688cb077791)
![ascll加密（乱码输出）](https://github.com/user-attachments/assets/5e5c20d5-5bb3-4318-afd4-34a4f21fd2a3)

![解密过程（输入）](https://github.com/user-attachments/assets/d3e3edc5-a38a-43d0-9580-d4b4720ac00b)
![解密过程（输出）](https://github.com/user-attachments/assets/1240d576-1eb8-4474-9233-776684256d9c)

3.交叉测试
经检验，和张函菘同学的测试结果相同，并能正常加解密，证明程序运行成功。

4.暴力破解
在运行栏中，我们可以通过输入明文密文对来输出密钥，可以破解出一组的明文密文的密钥，并得出运算时间和多组破解的平均时间

5. 封闭测试
对于随机选择的一个明密文对（011000110111100101101000、000011011101111110101001）
找到有不止一个密钥Key：0011000001、0111000001
![Uploading d768052d123ca0d0fe51aa96c4a6f16.png…]()

