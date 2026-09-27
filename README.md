# ✈️ Discrete-Event Airport Security & Stochastic Simulation in Java

### Kesikli Olay Sistem Simülasyonu, Kuyruk Ağları ve Rastgele Değişken Üreteçleri

Bu depo; Bilgisayar Mühendisliği **Sistem Simülasyonu ve Modelleme** dersi kapsamında Java ile sıfırdan geliştirilen **Gelecek Olay Listesi (Future Event List - FEL)** tabanlı havalimanı güvenlik kontrol noktası simülasyonunu, kuyruk performans metriklerini ve temel olasılık dağılım üreteçlerini içerir.

---

## 🛠️ Mimari ve Simülasyon Bileşenleri

| Paket / Modül               | Dosya                                         | Matematiksel Model & Algoritma                                                                                                                                        |
| :-------------------------- | :-------------------------------------------- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`discretesimulation...`** | `AirportSecuritySimulation.java`              | Öncelikli Kuyruk (`PriorityQueue`) ile FEL motoru. Varışlar: Düzgün Dağılım ($5 \pm 2$), Kontrol Süresi: Üstel Dağılım ($\mu=10$), Güvenlikten Geçememe Oranı: $\%5$. |
| **`discretesimulation...`** | `AirportSecuritySimulationGPSS.java`          | GPSS World blok mantığına paralel olay ve kuyruk durumu yönetimi.                                                                                                     |
| **`discretesimulation...`** | `Discrete.java` & `DiscreteSimulation...java` | Sıralı zaman atlamalı alternatif kuyruk ve inceleme simülasyonları.                                                                                                   |
| **`lab_10`**                | `ders.java`                                   | Düzgün Dağılım ($U[0, 10]$) rastgele değişken üretimi ve örneklem ortalaması doğrulaması.                                                                             |
| **`lab_10`**                | `ders2.java`                                  | Ters Dönüşüm Yöntemi (Inverse Transform Sampling) ile Üstel Dağılım üretimi: $X = -\frac{1}{\lambda} \ln(1 - U)$.                                                     |

---

## 📐 Simülasyon Matematiksel Modelleri

### 1. Ters Dönüşüm ile Üstel Dağılım (Exponential Variate Generation)

Kümülatif dağılım fonksiyonu $F(x) = 1 - e^{-\lambda x}$ olan üstel dağılım için $U \sim \text{Uniform}(0, 1)$ olmak üzere:

$$X = -\frac{1}{\lambda} \ln(1 - U) = -\mu \ln(1 - U)$$

### 2. Kuyruk İstatistikleri

- **Ortalama Kuyruk Uzunluğu ($\bar{L}_Q$):** Durum değişimlerinde biriken toplam kuyruk uzunluğunun toplam olay sayısına oranı.
- **Durdurma Kriteri:** Güvenlik kontrolünden geçişi reddedilen yolcu sayısı ($N_{\text{denied}} = 100$) hedefine ulaştığında simülasyon tamamlanır.

---

## 🔒 Copyright & License / Telif Hakkı Bildirimi

Bu proje ve simülasyon kodları **Proprietary (Tescilli / Tüm Hakları Saklıdır)** lisansına tabidir.

```text
Copyright (c) 2026 Muhammed Emin Korkunç. All Rights Reserved.

Bu projenin tüm kaynak kodları, simülasyon mantığı ve matematiksel
modelleme bileşenleri Muhammed Emin Korkunç'a aittir. Yazarın açık
yazılı izni olmaksızın kısmen veya tamamen kopyalanması, paylaşılması
veya ticari/akademik amaçla izinsiz kullanımı kesinlikle yasaktır.

👨‍💻 Geliştirici / Author
Muhammed Emin Korkunç

GitHub: @muhammedkorkunc

LinkedIn: Muhammed Emin Korkunç

Email: muhammedemin.korkunc@gmail.com
```
