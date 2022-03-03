package com.example.lib_compiler;


import com.example.annotationlib.BYView;
import com.example.annotationlib.DIActivity;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

@AutoService(Process.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({Constance.DIACTIVITY})
public class ByProcessor extends AbstractProcessor {
    private Elements elementsUtils;
    private Types typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementsUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (null != set) {
            Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(DIActivity.class);
            /*获取设置DIActivity注解的节点*/
            if (elementsAnnotatedWith != null) {
                //判断注解的节点是否为Activity
                TypeElement typeElement = elementsUtils.getTypeElement(Constance.Activity);
                for (Element element : elementsAnnotatedWith) {
                    /*获取注解节点的类的信息*/
                    TypeMirror typeMirror = element.asType();
                    /*获取注解信息*/
                    DIActivity annotation = element.getAnnotation(DIActivity.class);
                    /*注解在Activity的类上面*/
                    if (typeUtils.isSubtype(typeMirror, typeElement.asType())) {
                        /*获取节点的具体类型*/
                        TypeElement classElement = (TypeElement) element;
                        /*创建参数 Map<String,Class<? extends IRouteGroup>> routes*/
                        ParameterSpec altlas = ParameterSpec.builder(ClassName.get(typeMirror), "activity").build();
                        /*创建方法*/
                        MethodSpec.Builder method = MethodSpec.methodBuilder("findById")
                                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                .returns(TypeName.VOID)
                                .addParameter(altlas);
                        /*创建函数体*/
                        /*获取TypeElement的所有成员变量和成员方法*/
                        List<? extends Element> allMembers = elementsUtils.getAllMembers(classElement);

                        for (Element member : allMembers) {
                            BYView byView = member.getAnnotation(BYView.class);
                            if (byView == null) {
                                continue;
                            }
                            method.addStatement(String.format("activity.%s=(%s) activity.findViewById(%s)",
                                    member.getSimpleName(),//注解节点变量的名称
                                    ClassName.get(member.asType()).toString(),//注解节点变量的类型
                                    byView.value()));//注解的值
                        }

                        /*创建类*/
                        TypeSpec typeSpec = TypeSpec.classBuilder("ManagerFindBy" + element.getSimpleName())
                                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                                .addMethod(method.build())
                                .build();

                        /*创建Javaclass文件*/
                        JavaFile javaFile = JavaFile.builder("com.prim.find.by", typeSpec).build();

                        try {
                            javaFile.writeTo(processingEnv.getFiler());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        throw new IllegalArgumentException("@DIActivity must of Activity");
                    }
                }
            }
            return true;
        }
        return false;
    }
}
