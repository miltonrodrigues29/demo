package com.miltoncode;

import org.hibernate.sql.Update;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {
    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
        System.out.println("Hello");
    }
    @GetMapping
    public List<Customer> getCustomer()
    {
        return customerRepository.findAll();
    }

    record NewCustomerRequest(String name, String email, Integer age)
    {

    }
    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer id, @RequestBody NewCustomerRequest request)
    {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        System.out.println(existingCustomer);
        if(existingCustomer.isPresent())
        {
            Customer updatedCustomer = existingCustomer.get();
            if (request.name() != null)
            {
                updatedCustomer.setName(request.name());
            }

            if(request.email() != null)
            {
                updatedCustomer.setEmail(request.email());
            }

            if (request.age() != null)
            {
                updatedCustomer.setAge(request.age());
            }


            customerRepository.save(updatedCustomer);

        }

    }

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request)
    {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setAge(request.age());
        customer.setEmail(request.email());
        customerRepository.save(customer);
    }



    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id)
    {
        customerRepository.deleteById(id);
    }
    @GetMapping("/greet")
    public GreetResponse greet()
    {
        GreetResponse response =  new GreetResponse(
                "Hello", List.of("Java","Golang","Javascript"), new Person("Milton",23,8000)
        );
        return response;
    }

    @GetMapping("{customerId}")
    public Optional<Customer> getCustomerDetails(@PathVariable("customerId") Integer id)
    {

        return customerRepository.findById(id);

    }


    record Person(String name,int age, int availableCash)
    {

    }
    record GreetResponse(String greet, List<String> favProgrammingLanguages,Person person)
    {

    }


//    class GreetResponse
//{
//    private final String greet;
//    GreetResponse(String greet)
//    {
//        this.greet = greet;
//    }
//
//    public String getGreet()
//    {
//        return greet;
//    }
//
//    @Override
//    public String toString() {
//        return "GreetResponse{" +
//                "greet='" + greet + '\'' +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        GreetResponse that = (GreetResponse) o;
//        return Objects.equals(greet, that.greet);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(greet);
//    }
//}

}
