package com.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Contact;
import com.cms.repository.ContactRepository;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins={"http://localhost:3000"})
public class ContactController {
	@Autowired
	private ContactRepository contactRepository;
	
	//get all contacts
	@GetMapping("/contacts")
	public List<Contact> getAllcontacts(){
		return contactRepository.findAll();
	}
	//get employee by id
	@GetMapping("/contacts/{id}")
	public ResponseEntity <Contact> getContactById(@PathVariable String id){
		Contact contact = contactRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("Contact not exist with given id"));
		return ResponseEntity.ok(contact);
	}
	@PutMapping("/contacts/{id}")
	public ResponseEntity<Contact> updateContact(@PathVariable String id,@RequestBody Contact contactDetails){
		Contact contact = contactRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("Contact does not exists"));
		contact.setEmailId(contactDetails.getEmailId());
		contact.setFirstName(contactDetails.getFirstName());
		contact.setLastName(contactDetails.getLastName());
		Contact updatedContact = contactRepository.save(contact);
		return ResponseEntity.ok(updatedContact);
		
	}
	@PostMapping("/contacts")
	public ResponseEntity<Contact> addContact(@RequestBody Contact contactDetails){
		Contact addedContact = contactRepository.save(contactDetails);
		return ResponseEntity.ok(addedContact);
	}
	@DeleteMapping("/contacts/{id}")
	public ResponseEntity<String> deleteContact(@PathVariable String id){
		Contact contact = contactRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("Contact does not exists"));
		contactRepository.delete(contact);
		return ResponseEntity.ok("Contact deleted Successfully");
	}
}
