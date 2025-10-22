# API Pagination Guidelines

Bu servislerde çok sayıda kayıt döndürülmesi gereken uç noktalar `Spring Data`'nın `Pageable` soyutlamasını kullanır. Böylece istemciler sonuçları sayfalara bölerek tüketebilir ve tek istekte aşırı veri taşımaktan kaçınır.

## Varsayılan Davranış

- Varsayılan sayfa büyüklüğü 20 kayıttır.
- İstemci `page` ve `size` parametrelerini göndermediğinde, ilk sayfa (`page=0`) ve 20 kayıt döner.
- Bu varsayılan, `@PageableDefault(size = 20)` anotasyonu ve `spring.data.web.pageable.default-page-size` yapılandırması ile uygulandı.

## Parametre Kullanımı

Herhangi bir listeleme uç noktasına aşağıdaki sorgu parametreleri gönderilebilir:

| Parametre | Açıklama | Varsayılan |
|-----------|----------|------------|
| `page`    | 0 tabanlı sayfa numarası | `0` |
| `size`    | Sayfa başına kayıt sayısı | `20` |
| `sort`    | `property,ASC|DESC` formatında sıralama bilgisi | Entity varsayılan sıralaması |

Örnek istek:

```http
GET /api/v1/students?page=1&size=10&sort=lastName,asc
```

Bu istek ikinci sayfadaki (0 tabanlı) 10 öğrenciyi soyadlarına göre artan sıralı olarak döndürür.

## Etkilenen Uç Noktalar

Aşağıdaki uç noktalar artık varsayılan olarak 20 kayıt döndürür, fakat isteğe göre `size` parametresiyle özelleştirilebilir:

- `GET /api/v1/students`
- `GET /api/v1/courses`
- `GET /api/v1/instructors`
- `GET /api/v1/sections`
- `GET /api/v1/enrollments/by-student/{studentId}`
- `GET /api/v1/enrollments/by-course/{courseId}`

İstemciler `page` ve `size` parametreleriyle bu sayfaların üzerinden gezinerek tüm sonuçları elde edebilir.

