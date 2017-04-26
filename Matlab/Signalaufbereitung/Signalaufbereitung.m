% Mittelwert
y=y10;
n=50;

y_neu=zeros(size(y));
for a=1:length(y)
    count=0;
    for b=a-n:a+n
        if b>0 && b<=length(y)
            y_neu(a)= y(b) + y_neu(a);
            count=count+1;
        end
    end
    y_neu(a)=y_neu(a)/count;
end
figure
subplot(211)
   plot(t10, y_neu)
subplot(212)
       plot(t10, y10)
       size(y)
       size(y_neu)
       
 %%
%  Median
y=y10;
n=5;

for a=1:length(y)
    M=zeros(1,1);
    count=1;
    for b=a-n:a+n
        if b>0 && b<=length(y)
            M(count)=y(b);
            count=count+1;
        end
    end
    y_neu(a)=median(M);
end
figure
subplot(211)
   plot(t10, y_neu)
subplot(212)
       plot(t10, y10)
       size(y)
       size(y_neu)
       a='hey'
       
%%
% Abschneiden vorne

% Bestimmt den Mittelwert aus n nachfolgenden Werten und vergleicht die
% Abweichung zum Messwert mit einem vorgegebenen Verhältniss q. Wenn das
% Verhältniss grösser ist wird das Signal bis dahin abgschnitten.

n=50;       %anzahl zu mittelnde Werte
c=1;        %Reverenzmesswert
q=0.5;      %max. Abweichung bevor Abschneidung im Verhältniss 
y_alt=y10;  
m=y_alt(1); %startwert, damit in die while-Schleife gesprungen wird

% Mittelwert der nächsten n Werte bestimmen und mit Wert vergleichen
while abs(y_alt(c)-m)/y_alt(c) < q && length(y_alt)>=c+n
    m=0;
    for a=1:n
        m=m+y_alt(c+n);
    end
    m=m/n;
    c=c+1;
end

c=c-1;      % Anzahl abzuschneidende Messwerte

% Neue 
y_neu=zeros(length(y_alt)-c,1);
for a=1:length(y_alt)-c
    y_neu(a)=y_alt(a+c);
end
t_alt=t10;
t_neu=zeros(length(t_alt)-c,1);
for a=1:length(t_alt)-c
    t_neu(a)=t_alt(a+c)-t_alt(c);
end

subplot(211)
plot(t_neu, y_neu);
subplot(212)
plot(t_alt, y_alt);


%%
%abschneiden hinten
close all
n=10;
c=1;
q=0.25;
y_alt=y_neu;
m=y_alt(1);
while abs(y_alt(end-c)-m)/y_alt(end-c) < q && length(y_alt)>=c+n
    m=0;
    for a=1:n
        m=m+y_alt(end-c-n);
    end
    m=m/n;
    c=c+1;
end
c=c-1;
y_neu=zeros(length(y_alt)-c,1);
for a=1:length(y_alt)-c
    y_neu(a)=y_alt(a);
end
t_alt=t10;
t_neu=zeros(length(t_alt)-c,1);
for a=1:length(t_alt)-c
    t_neu(a)=t_alt(a);
end
subplot(211)
plot(t_neu, y_neu);
subplot(212)
plot(t_alt, y_alt);


