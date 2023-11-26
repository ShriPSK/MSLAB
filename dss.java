import java.io.*;
import java.util.*;
public class dss
{
public static int inverse(int b,int m)
{
int q,a1,a2,a3,b1,b2,b3,t1,t2,t3;
a1=1;
a2=0;
a3=m;
b1=0;
b2=1;
b3=b;
while(b3!=1)
{
q=a3/b3;
t1=a1-(q*b1);
t2=a2-(q*b2);
t3=a3-(q*b3);
a1=b1;
a2=b2;
a3=b3;
b1=t1;
b2=t2;
b3=t3;
}
return b2;
}
public static void main(String args[])
{
Scanner sc=new Scanner(System.in);
System.out.println("Enter the value of h(m),p,h,k,q,x");
int hf=sc.nextInt();
int p=sc.nextInt();
int h=sc.nextInt();
int k=sc.nextInt();
int q=sc.nextInt();
int x=sc.nextInt();
int pow=(p-1)/q;
int g=1;
for(int i=0;i<pow;i++)
{
g=(g*h)%p;
}
System.out.println(g);
System.out.println("global public keys:"+" "+p+","+q+","+g);
int y=1;
for(int i=0;i<x;i++)
{
y=(y*g)%p;
}
System.out.println("public key"+" "+y);
int r1=1;
int r=0;
for(int i=0;i<k;i++)
{
r1=(r1*g)%p;
r=r1%q;
}
System.out.println("value of r"+" "+r);
int invert=inverse(k,q);
int t=x*r;
int s1=invert*(hf+t);
int st=-s1;
while(st>q)
st=st%q;
int s=q-st;
System.out.println("value of s"+" "+s);
int w=inverse(s,q);
System.out.println("w"+w);
int n1=(hf*w)%q;
//System.out.println("n1"+n1);
int n2=(r*w);
int st1=-n2;
if(st1>q){
st1=st1%q;}
int n21=q-st1;
//System.out.println("n2"+n21);
int v1=1;
for(int i=0;i<n1;i++)
{
v1=v1*g;
}
int v2=1;
for(int i=0;i<n21;i++)
{
v2=v2*y;
}
int v3=(v1*v2)%p;
int v=v3%q;
System.out.println("value of v"+" "+v);
if(r==v)
System.out.println("Dss verified");
else
System.out.println("Dss not verified");
}
}