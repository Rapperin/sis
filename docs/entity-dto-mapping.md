# Entity ↔ DTO Dönüşümleri

Bu projede veri aktarım nesneleri (DTO) ile JPA varlıklarını ayırmak; kalıcı katmanla sunum/istek modellerinin bağımlılığını azaltır, doğrulama ve sürümleme işlerini kolaylaştırır. Aşağıdaki örnek, `Student` varlığı ile `StudentDto` arasında dönüşümü gerçekleştiren bir eşleyiciyi göstermektedir.

```java
@Component
public class StudentMapper {

    public StudentDto toDto(Student s) {
        return new StudentDto(
                s.getId(),
                s.getFirstName(),
                s.getLastName(),
                s.getEmail(),
                s.getBirthDate()
        );
    }

    public Student toEntity(StudentDto d) {
        Student s = new Student();
        updateEntity(d, s);
        s.setId(d.id());
        return s;
    }

    public void updateEntity(StudentDto d, Student target) {
        target.setFirstName(d.firstName());
        target.setLastName(d.lastName());
        target.setEmail(d.email());
        target.setBirthDate(d.birthDate());
    }
}
```

Servis katmanı, bu bileşeni enjekte ederek hem yeni kayıt oluştururken hem de güncellerken dönüşüm mantığını yeniden kullanabilir:

```java
@Service
public class StudentService {

    private final StudentRepository repo;
    private final StudentMapper mapper;

    public StudentService(StudentRepository repo, StudentMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public StudentDto create(StudentDto dto) {
        Student entity = mapper.toEntity(dto);
        entity = repo.save(entity);
        return mapper.toDto(entity);
    }

    public StudentDto update(Long id, StudentDto dto) {
        Student entity = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("student_not_found"));
        mapper.updateEntity(dto, entity);
        return mapper.toDto(repo.save(entity));
    }
}
```

> İpucu: Proje büyüdükçe `MapStruct` gibi araçlarla bu eşleme sınıflarını otomatik üretmeyi düşünebilirsiniz. Ancak manuel yaklaşım, mantığın kolayca özelleştirilmesini sağlar.
