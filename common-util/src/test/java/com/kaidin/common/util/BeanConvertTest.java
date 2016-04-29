package com.kaidin.common.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.kaidin.common.util.help.Student;

public class BeanConvertTest {
	private static String[] PROPERTIES = new String[]{"name", "age", "studentNumber"};
	private static Object[] VALUES = new Object[]{"张三", 12, "1234567890"};
	
	
	@Test
	public void testConvertTStringArrayObjectArray() throws Exception {
		BeanConvert<Student> beanConvert = new BeanConvert<Student>();
		Student student = new Student();
		beanConvert.convert(student, PROPERTIES, VALUES);
		
		assertEquals("张三", student.getName());
		assertEquals(12, student.getAge());
		assertEquals("1234567890",student.getStudentNumber());
	}

	@Test
	public void testConvertClassOfTStringArrayListOfObject() throws Exception {
		List<Object[]> valuesList = new ArrayList<Object[]>(2);
		valuesList.add(VALUES);
		valuesList.add(new Object[]{"李四", 20, "abcdefthij"});
		
		BeanConvert<Student> beanConvert = new BeanConvert<Student>();
		List<Student> studentList = beanConvert.convert(Student.class, PROPERTIES, valuesList);
		
		assertEquals("张三", studentList.get(0).getName());
		assertEquals(12, studentList.get(0).getAge());
		assertEquals("1234567890", studentList.get(0).getStudentNumber());
		assertEquals("李四", studentList.get(1).getName());
		assertEquals(13, studentList.get(1).getAge());
		assertEquals("abcdefthij", studentList.get(1).getStudentNumber());
	}
	
	@Test
	public void testConvertClassOfTStringArrayListOfObject2() throws Exception {
		List<Object[]> valuesList = new ArrayList<Object[]>(2);
		valuesList.add(VALUES);
		valuesList.add(new Object[]{"李四", 20, "abcdefthij"});
		
		BeanConvert<Student> beanConvert = new BeanConvert<Student>(new BeanConvertFilter<Student>() {
			@Override
			public boolean doFilter(Student student) {
				String name = student.getName();
				student.setName("学生：" + name);
				if (18 > student.getAge()) {
					return false;	// 小于18岁被过滤
				} else {
					return true;
				}
			}
		});
		List<Student> studentList = beanConvert.convert(Student.class, PROPERTIES, valuesList);
		
		assertEquals(1, studentList.size());
		assertEquals("学生：李四", studentList.get(0).getName());
		assertEquals(20, studentList.get(0).getAge());
		assertEquals("abcdefthij", studentList.get(0).getStudentNumber());
	}
}
