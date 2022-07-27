package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.EntityNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;
	
	@Transactional(readOnly = true)
	public List<EventDTO> findAll(){
		List<Event> result = repository.findAll(Sort.by("name"));
		List<EventDTO> list = result.stream().map(x -> new EventDTO(x)).collect(Collectors.toList());
		return list;
	}
	
	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {
			Event event = repository.getOne(id);
			event.setName(dto.getName());
			event.setUrl(dto.getUrl());
			event.setDate(dto.getDate());
			event.setCity(new City(dto.getCityId(), null));
			return new EventDTO(event);
		} catch (javax.persistence.EntityNotFoundException e) {
			throw new EntityNotFoundException("id not found " + id);
		}
	}
}
