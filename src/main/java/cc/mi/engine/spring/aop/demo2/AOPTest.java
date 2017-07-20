package cc.mi.engine.spring.aop.demo2;

public class AOPTest {
	public static void main(String[] args) {
		AOPInstrumenter2 instrumenter=new AOPInstrumenter2();
		StudentInfoServiceImpl studentInfo=(StudentInfoServiceImpl)instrumenter.getInstrumentedClass(StudentInfoServiceImpl.class);
		studentInfo.findInfo("阿飞");
	}
}
