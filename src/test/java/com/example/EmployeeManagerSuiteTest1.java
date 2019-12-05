package com.example;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import service.BankService;
import service.Employee;
import service.EmployeeManager;
import service.EmployeeRepository;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.validateMockitoUsage;

/**
 * https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#1
 */

@RunWith(MockitoJUnitRunner.class)
public class EmployeeManagerSuiteTest1 {

    @Spy
    @InjectMocks
    private static EmployeeManager employeeManager;
    @Mock
    private static EmployeeRepository employeeRepository;
    @Mock
    private static BankService bankService;

    @Test
    public void testingSpy() {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(0, employeeManager.payEmployees());
    }

    @Test
    public void verifyBehaviour_1(){
        employeeManager.payEmployees();
        verify(employeeRepository).findAll();
    }

    @Test
    public void stub_interactions_2(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id",3)));
        assertEquals(0, employeeManager.payEmployees());
    }

    @Test
    public void stub_with_unecessary_repetition_2(){
        when(employeeRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        assertEquals(0,employeeManager.payEmployees());
    }

    @Test
    public void stub_without_unecessary_repetition_2(){
        assertEquals(0,employeeManager.payEmployees());
    }

    @Test
    public void stub_order_important_PARTI_2_arg_matcher_3(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id",3)));
        when(bankService.pay(anyString(), any(double.class))).thenReturn(3d);
        assertEquals(3, employeeManager.payEmployees());
    }

    public class LocalArgMatcher implements ArgumentMatcher<String>{
        @Override
        public boolean matches(String s) {
            return s.equals("id");//si id_false, le test est KO
        }
    }

    //TODO:commentaire $2
    @Test
    public void argument_matcher_3(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id",3)));
        when(bankService.pay(argThat(new LocalArgMatcher()), any(double.class))).thenReturn(3d);
        assertEquals(3, employeeManager.payEmployees());
    }

    @Test
    public void argument_matcher_eq_3(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id",3)));
        //when(bankService.pay(eq("id_false"), any(double.class))).thenReturn(3d);//org.mockito.exceptions.misusing.UnnecessaryStubbingException:
        assertEquals(0, employeeManager.payEmployees());
    }

    @Test
    public void verify_exact_nbre_invocations_4(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id1",3)
                ,new Employee("id2",5)));
        when(bankService.pay(anyString(), any(double.class))).thenReturn(5d);

        assertEquals(10, employeeManager.payEmployees());

        verify(employeeRepository, times(2)).put(any(Employee.class));
    }

    @Test
    public void verify_one_invocation_4(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id1",3)));
        when(bankService.pay(anyString(), any(double.class))).thenReturn(5d);

        assertEquals(5, employeeManager.payEmployees());

        verify(employeeRepository, times(1)).put(any(Employee.class));//times(1) unecessary
    }

    @Test(expected = RuntimeException.class)
    public void stub_void_method_with_exception_5(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id1",3)));

        // non applicable
        // when(employeeRepository.put(any(Employee.class))).thenThrow(new RuntimeException());
        doThrow(new RuntimeException()).when(employeeRepository).put(any());
        employeeManager.payEmployees();
    }

    @Test
    public void verify_in_order_6(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id1",3)));
        InOrder inOrder = inOrder(employeeRepository, bankService);

        employeeManager.payEmployees();

        inOrder.verify(employeeRepository).findAll();
        //inOrder.verify(bankService.pay(any(),any()));//KO hint:NPE mockito tests c'est quand c'est mal utilisé
        inOrder.verify(bankService).pay(anyString(),any(double.class));//lire la signature de la methode Intellij
        //inOrder.verify(bankService).pay(any(),any());//KO; il faut preciser les types
    }

    @Test
    public void verify_interactions_never_happened_on_mock_7(){
        employeeManager.payEmployees();

        verifyZeroInteractions(bankService);
    }

    @Test
    public void find_redundant_invocations_8(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id1",3)));

        employeeManager.payEmployees();

        verify(bankService).pay(anyString(), any(double.class));
        verifyNoMoreInteractions(bankService);
    }


    @Test
    public void stubbing_consecutive_calls_10(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id1",3)
                ,new Employee("id2",5)));
        when(bankService.pay(anyString(), any(double.class))).thenReturn(5d).thenReturn(8d);

        assertEquals(13,employeeManager.payEmployees());
    }

    @Test
    public void stubbing_consecutive_methods_overrides_10(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id1",3)
                ,new Employee("id2",5)));
        when(bankService.pay(anyString(), any(double.class))).thenReturn(5d);
        when(bankService.pay(anyString(), any(double.class))).thenReturn(8d);

        assertEquals(16,employeeManager.payEmployees());
    }

    @Test//TODO : override with real case
    public void stubbing_with_callbacks_11(){
        when(employeeRepository.findAll()).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return Arrays.asList(new Employee("id1",3));
            }
        });
        assertEquals(0,employeeManager.payEmployees());
    }

    @Test(expected = RuntimeException.class)
    public void do_something_family_of_methods_for_void_methods_12(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id1",3)
                ,new Employee("id2",5)));
        doThrow(IllegalArgumentException.class).when(employeeRepository).put(any(Employee.class));
        employeeManager.payEmployees();
    }

    @Test
    public void do_something_family_of_methods_for_spied_method_12(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id1",3)));
        doReturn(12d).when(employeeManager).payEmployeesImpl(any(Employee.class));
        assertEquals(12,employeeManager.payEmployees());
    }

    @Test
    public void spying_real_objects_limitation_for_final_method_13(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id1",3)));
        //doReturn(1d).when(employeeManager).printTransaction(any(Employee.class));//decommenter pour voir le pb avec "final"
        doReturn(12d).when(employeeManager).payEmployeesImpl(any(Employee.class));
        assertEquals(12,employeeManager.payEmployees());
    }

    @Test
    public void changing_default_return_values_to_unstubbed_invocations_14(){
        employeeRepository = mock(EmployeeRepository.class, RETURNS_SMART_NULLS);//si on n'ajoute pas ça, on a une NPE
        //when(employeeRepository.findAll()).thenReturn(null);//a decommenter pour montrer ce que ça donne
        employeeManager.payEmployees();
    }

    @Test
    public void capturing_arg_for_further_asumptions_15(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id1",3)));
        employeeManager.payEmployees();

        ArgumentCaptor<String> argCaptor = ArgumentCaptor.forClass(String.class);//creating argCapture for employee and having it's value doesn't work
        verify(bankService).pay(argCaptor.capture(), any(double.class));

        assertEquals("id1", argCaptor.getValue());
    }

    @Ignore // TODO : corriger ce test
    @Test//previously considered as code smell
    public void real_partial_mocks_16(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id1",3)));

        //when(bankService.pay(anyString(), any(double.class))).thenCallRealMethod();//not possible because abstract method
        when(bankService.pay(anyString(), any(double.class))).thenReturn(3d);

        when(employeeManager.payEmployees()).thenCallRealMethod();//la methode est appelee a ce moment; quel est l'interet??
    }

    @Test
    public void reset_mocks_17(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id1",3)));
        when(bankService.pay(anyString(), any(double.class))).thenReturn(3d);
        assertEquals(3,employeeManager.payEmployees());

        reset(employeeRepository, bankService);
        assertEquals(0,employeeManager.payEmployees());
    }

    @Test
    public void validate_mockito_usage_18() {
        when(employeeRepository.findAll());
        validateMockitoUsage();//TODO : justifier si j'utilise pas de Runner mockito
    }

    @Test
    public void aliases_behaviour_driven_development_19(){
        //given
        given(employeeRepository.findAll()).willReturn(Arrays.asList(new Employee("id1",3)));
        given(bankService.pay(anyString(), any(double.class))).willReturn(3d);

        //when
        int payments = employeeManager.payEmployees();

        //then
        assertEquals(3,payments);
    }

    @Test // TODO : quelle utilite ?
    public void serializable_mocks_20(){
        employeeRepository = mock(EmployeeRepository.class, withSettings().serializable());
    }

    @Test
    public void New_annotations_Captor_Spy_InjectMocks(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("id1",3)));
        when(bankService.pay(anyString(), any(double.class))).thenReturn(3d);

        employeeManager.payEmployees();

        verify(employeeRepository).findAll();
    }

}
