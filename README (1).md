# Java Socket Tabanlı Basit Web Sunucusu

Bu proje, **üçüncü parti hiçbir kütüphane veya framework kullanılmadan**, yalnızca **Java** ve **Socket programlama** ile yazılmış basit bir web sunucusudur. Sunucu **1989. port** üzerinden çalışır. Herhangi bir web tarayıcısı sunucuya bağlandığında, HTML ile biçimlendirilmiş bir biyografi sayfası görüntülenir.

- **Hazırlayan:** Ad Soyad
- **Öğrenci No:** 123456789
- **Ders:** Ders Adı

---

## İçindekiler

1. [Projenin Amacı](#projenin-amaci)
2. [Özellikler](#ozellikler)
3. [Proje Yapısı](#proje-yapisi)
4. [Çalışma Prensibi](#calisma-prensibi)
5. [Sınıfların Açıklamaları](#siniflarin-aciklamalari)
6. [Socket, Port ve IP Kavramları](#socket-port-ve-ip-kavramlari)
7. [SOLID Prensiplerinin Uygulanması](#solid-prensiplerinin-uygulanmasi)
8. [Kurulum ve Çalıştırma](#kurulum-ve-calistirma)

---

## Projenin Amacı

Java programlama dili ve socket programlama kullanarak, hazır bir web sunucusuna ihtiyaç duymadan basit bir web sunucusu oluşturmaktır. Sunucuya tarayıcıdan `http://localhost:1989` adresiyle erişildiğinde:

- **H1** boyutunda ad soyad,
- **H2** boyutunda öğrenci numarası,
- Renk, font çeşidi ve gölge gibi HTML/CSS bileşenleriyle biçimlendirilmiş kısa bir biyografi

gösterilir.

## Özellikler

- Yalnızca Java standart kütüphanesi kullanılmıştır (`java.net`, `java.io`, `java.util.concurrent`).
- Sunucu **1989. portu** dinler.
- Birden fazla tarayıcı bağlantısı **thread havuzu** ile aynı anda işlenir.
- Her sınıf ayrı bir dosyada tanımlanmıştır.
- **SOLID** prensiplerine uygun tasarlanmıştır.
- Bilinmeyen adresler için **404 Not Found** cevabı döndürülür.

## Proje Yapısı

```
src/main/java/webserver/
 ├── Main.java            → Programın başlangıç noktası
 ├── WebServer.java       → Bağlantıları kabul eden sunucu sınıfı
 ├── ClientHandler.java   → Her tarayıcı bağlantısını işleyen sınıf
 ├── HttpRequest.java     → Gelen HTTP isteğini ayrıştırır
 ├── HttpResponse.java    → HTTP cevabını hazırlar
 ├── PageProvider.java    → Sayfa sağlayıcı arayüzü (interface)
 ├── BiographyPage.java   → Biyografi HTML sayfasını üretir
 └── ServerConfig.java    → Sunucu ayarlarını (port) tutar
```

## Çalışma Prensibi

Tarayıcıya `http://localhost:1989` yazıldığında sırasıyla şu adımlar gerçekleşir:

```
Tarayıcı
   │  (1) TCP bağlantısı kurar
   ▼
WebServer ──► ServerSocket.accept() ile bağlantıyı kabul eder
   │  (2) Bağlantıyı thread havuzuna verir
   ▼
ClientHandler ──► HttpRequest ile "GET / HTTP/1.1" satırını okur
   │  (3) İstenen yola göre sayfayı seçer
   ▼
PageProvider (BiographyPage) ──► HTML içeriğini üretir
   │  (4) HTML'i HTTP formatına paketler
   ▼
HttpResponse ──► Başlık (header) + içerik (body) baytlarını oluşturur
   │  (5) Cevabı gönderir ve bağlantıyı kapatır
   ▼
Tarayıcı sayfayı gösterir
```

## Sınıfların Açıklamaları

| Sınıf | Tür | Görevi |
|---|---|---|
| `Main` | Class | Nesneleri oluşturur, birbirine bağlar ve sunucuyu başlatır. |
| `ServerConfig` | Class | Port numarasını tutar. `private final` alan ve `getPort()` metodu ile kapsüllenmiştir. |
| `PageProvider` | Interface | `getHtml()` metodunu tanımlayan sözleşmedir. Farklı sayfa sınıfları bu arayüzü uygular. |
| `BiographyPage` | Class | `PageProvider` arayüzünü uygular. Ad, öğrenci no ve biyografiyi HTML/CSS ile üretir. |
| `HttpRequest` | Class | Tarayıcıdan gelen ilk satırı okuyup yöntem (GET) ve yolu (/) ayırır. |
| `HttpResponse` | Class | Durum kodu (200, 404), başlıklar ve içerikten oluşan HTTP cevabını bayt dizisi olarak üretir. |
| `ClientHandler` | Class | `Runnable` arayüzünü uygular. Her bağlantıyı ayrı bir thread içinde işler. |
| `WebServer` | Class | `ServerSocket` ile portu dinler, `accept()` ile bağlantıları kabul eder ve thread havuzuna iletir. |

### Kullanılan Önemli Java Nesneleri

| Nesne | Ne İşe Yarar? |
|---|---|
| `ServerSocket` | Belirtilen portu dinleyerek istemci bağlantılarını bekler. |
| `Socket` | Sunucu ile tarayıcı arasındaki bağlantının uç noktasıdır. |
| `InputStreamReader` / `BufferedReader` | Tarayıcıdan gelen baytları karaktere çevirir ve satır satır okumayı sağlar. |
| `OutputStream` | Tarayıcıya cevabı bayt olarak gönderir. |
| `ExecutorService` | Thread havuzu oluşturarak birden fazla bağlantıyı aynı anda işler. |
| `Runnable` | Bir sınıfın ayrı iş parçacığında çalışabilmesini sağlar. |
| `ByteArrayOutputStream` | Başlık ve içerik baytlarını tek bir bayt dizisinde birleştirir. |
| `StandardCharsets.UTF_8` | Türkçe karakterlerin doğru gösterilmesi için karakter kodlamasını belirler. |

## Socket, Port ve IP Kavramları

### IP Adresi

IP adresi, ağdaki her cihazın benzersiz kimliğidir (örneğin `192.168.1.5`). Bir apartmanın adresine benzetilebilir. `localhost` veya `127.0.0.1` ise bilgisayarın kendisini ifade eden özel adrestir.

### Port

Port, bir cihaz içinde hangi uygulamaya bağlanılacağını belirleyen 0 – 65535 arasındaki numaradır. Apartmandaki daire numarasına benzetilebilir. Aynı IP adresinde birçok program çalışabilir ve gelen veri port numarasına göre doğru programa yönlendirilir.

| Aralık | Adı | Örnek |
|---|---|---|
| 0 – 1023 | Well-known (bilinen) portlar | 80 (HTTP), 443 (HTTPS), 22 (SSH) |
| 1024 – 49151 | Registered (kayıtlı) portlar | 3306 (MySQL), 8080 |
| 49152 – 65535 | Dynamic / Private portlar | Geçici bağlantılar |

Bu projede kullanılan **1989. port**, kayıtlı portlar aralığındadır ve standart bir servise ayrılmamıştır. Standart HTTP portu 80 olduğu için tarayıcıda adresin sonuna `:1989` yazılması gerekir.

### Socket

Socket, iki programın ağ üzerinden haberleşmesini sağlayan uç noktadır; **IP adresi + port numarası** birleşimiyle tanımlanır. Java'da:

- `ServerSocket`: Sunucu tarafında belirli bir portu dinler.
- `Socket`: Bağlanan her istemci için oluşturulan bağlantı nesnesidir.
- `accept()`: Bir istemci bağlanana kadar bekler ve bağlantı kurulunca o istemciye özel `Socket` döndürür.

## SOLID Prensiplerinin Uygulanması

| Prensip | Bu Projedeki Uygulaması |
|---|---|
| **S** – Single Responsibility (Tek Sorumluluk) | Her sınıfın yalnızca tek bir görevi vardır: `HttpRequest` isteği ayrıştırır, `HttpResponse` cevabı üretir, `BiographyPage` HTML hazırlar, `ClientHandler` bağlantıyı işler, `WebServer` bağlantı kabul eder, `ServerConfig` ayarları tutar. |
| **O** – Open/Closed (Açık/Kapalı) | Yeni bir sayfa eklemek için mevcut sınıfları değiştirmeden `PageProvider` arayüzünü uygulayan yeni bir sınıf yazmak yeterlidir. |
| **L** – Liskov Substitution (Yerine Geçme) | `BiographyPage`, `PageProvider` yerine sorunsuz kullanılabilir. `ClientHandler` hangi sayfa sınıfının geldiğini bilmeden çalışır. |
| **I** – Interface Segregation (Arayüz Ayrımı) | `PageProvider` yalnızca `getHtml()` metodunu içeren küçük bir arayüzdür. Sınıflar kullanmayacakları metotları uygulamaya zorlanmaz. |
| **D** – Dependency Inversion (Bağımlılığın Tersine Çevrilmesi) | `WebServer` ve `ClientHandler`, somut `BiographyPage` yerine `PageProvider` soyutlamasına bağlıdır. Nesneler `Main` içinde oluşturulup constructor ile dışarıdan verilir. |

## Kurulum ve Çalıştırma

### Gereksinimler

- Java JDK 17 veya üzeri
- IntelliJ IDEA (veya herhangi bir Java IDE'si)

### Adımlar

1. Depoyu bilgisayarınıza indirin:

   ```bash
   git clone https://github.com/KULLANICI_ADI/DEPO_ADI.git
   ```

2. Projeyi IntelliJ IDEA ile açın.
3. `src/main/java/webserver/Main.java` dosyasını açın ve `main` metodunu çalıştırın.
4. Konsolda şu mesajı görmelisiniz:

   ```
   Sunucu başlatıldı. Port: 1989
   ```

5. Tarayıcıda şu adresi açın:

   ```
   http://localhost:1989
   ```

### Başka cihazdan erişim

Aynı ağdaki başka bir cihazdan erişmek için bilgisayarın IP adresini öğrenin (Windows'ta `ipconfig`) ve `http://IP_ADRESI:1989` adresini açın. Windows Güvenlik Duvarı uyarı verirse erişime izin verin.

---

## Notlar

- Sunucu yalnızca `GET /` isteğine biyografi sayfasıyla cevap verir. Diğer adresler için `404 Not Found` döner.
- Tarayıcılar otomatik olarak `/favicon.ico` isteyebilir. Bu istek de 404 alır ve normaldir.
- Bu proje herhangi bir üçüncü parti bileşen içermez.
