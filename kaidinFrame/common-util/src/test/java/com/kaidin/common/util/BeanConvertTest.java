package com.kaidin.common.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.kaidin.common.util.help.Student;

public class BeanConvertTest {

	@Test
	public void testConvertTStringArrayObjectArray() throws Exception {
		String[] properties = new String[]{"name", "age", "studentNumber"};
		Object[] values = new Object[]{"张三", 12, "1234567890"};
		
		BeanConvert<Student> beanConvert = new BeanConvert<Student>();
		Student student = new Student();
		beanConvert.convert(student, properties, values);
		
		assertEquals("张三", student.getName());
		assertEquals(12, student.getAge());
		assertEquals("1234567890",student.getStudentNumber());
	}

	@Test
	public void testConvertClassOfTStringArrayListOfObject() throws Exception {
		String[] properties = new String[]{"name", "age", "studentNumber"};
		List<Object[]> valuesList = new ArrayList<Object[]>(2);
		valuesList.add(new Object[]{"张三", 12, "1234567890"});
		valuesList.add(new Object[]{"李四", 13, "abcdefthij"});
		
		BeanConvert<Student> beanConvert = new BeanConvert<Student>();
		List<Student> studentList = beanConvert.convert(Student.class, properties, valuesList);
		
		assertEquals("张三", studentList.get(0).getName());
		assertEquals(12, studentList.get(0).getAge());
		assertEquals("1234567890", studentList.get(0).getStudentNumber());
		assertEquals("李四", studentList.get(1).getName());
		assertEquals(13, studentList.get(1).getAge());
		assertEquals("abcdefthij", studentList.get(1).getStudentNumber());
	}
	
	@Test
	public void testConvertClassOfTStringArrayListOfObject2() throws Exception {
		String[] properties = new String[]{"name", "age", "studentNumber"};
		List<Object[]> valuesList = new ArrayList<Object[]>(2);
		valuesList.add(new Object[]{"张三", 12, "1234567890"});
		valuesList.add(new Object[]{"李四", 13, "abcdefthij"});
		
		BeanConvert<Student> beanConvert = new BeanConvert<Student>(new BeanConvertFilter<Student>() {
			@Override
			public boolean doFilter(Student student) {
				student.setName("学生：" + student.getName());
				
				return true;
			}
		});
		List<Student> studentList = beanConvert.convert(Student.class, properties, valuesList);
		
		assertEquals("学生：张三", studentList.get(0).getName());
		assertEquals(12, studentList.get(0).getAge());
		assertEquals("1234567890", studentList.get(0).getStudentNumber());
		assertEquals("学生：李四", studentList.get(1).getName());
		assertEquals(13, studentList.get(1).getAge());
		assertEquals("abcdefthij", studentList.get(1).getStudentNumber());
	}
}
