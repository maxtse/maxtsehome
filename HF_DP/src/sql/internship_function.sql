set names utf8;
use `internship_management`;
drop procedure if exists year_internship_pro;
drop procedure if exists year_not_internship_pro;
drop procedure if exists period_not_internship;
drop procedure if exists get_all_area_internship_count;
drop procedure if exists get_area_internship_count;
drop procedure if exists has_internship_company;
drop procedure if exists year_company_internship_count;
delimiter //
#1.获取每年实习的人数
create PROCEDURE year_internship_pro()
begin
		declare year_intership_count int default 0;
		select count(distinct(`sutdent_no`))  from `tb_internship` where YEAR(internship_start_date)=YEAR(NOW()) and `internship_company_id` > 0 into year_intership_count;
		update statistical_table1 set year_internship_student_count=year_intership_count;
	end;
#2.获取每年没有实习的人数
create PROCEDURE year_not_internship_pro()
begin
		declare year_not_intership_count int default 0;
		select count(distinct(`sutdent_no`))  from `tb_internship` where YEAR(internship_start_date)=YEAR(NOW()) and `internship_company_id` = 0 into year_not_intership_count;
		update statistical_table1 set year_not_internship_student_count=year_not_intership_count;
	end;
#3.获取去年某时间段内没有实习的人数
create procedure period_not_internship(in start_date timestamp, in end_date timestamp, out internship_count int)
	begin
	set internship_count= 0;
	select count(distinct(`sutdent_no`)) from `tb_internship` where  date_add(start_date, interval -1 year) between `internship_start_date`  and `internship_end_date` or date_add(end_date, interval -1 year) between `internship_start_date`  and `internship_end_date` and `internship_company_id` = 0 into internship_count;
	end;
#4.获取过去n年中每个公司招收的实习生人数 参数：n
create procedure year_company_internship_count(in n int)
	begin
		declare done int default 0;
		declare c_id bigint;
		declare c_name varchar(30);
		declare year_company_count int default 0;
		declare cur1 cursor for select id,company from tb_internship_company;
		declare continue handler for not found set done=1;
		delete from statistical_table3; 
		open cur1;
		compantyLoop:loop
			fetch cur1 into c_id,c_name;
			if done=1
			then leave compantyLoop;
			end if;
			select count(distinct(`sutdent_no`)) from tb_internship where internship_company_id = c_id 
			and internship_end_date > date_add(now(), interval -n year) into year_company_count;
			insert into statistical_table3 values(c_name,year_company_count);
			end loop compantyLoop;
		close cur1;
		select company_name,internship_count from statistical_table3;
	end;
#5.获取某区域内实习生个数 这里必须6先执行
create procedure get_area_internship_count(in city_in varchar(20), in department_in varchar(20), out internship_count_out int)
	begin
	call get_all_area_internship_count();
	select internship_count from statistical_table2 where city=city_in and department = department_in into internship_count_out;
	end;
#6.获取所有的区域的实习人数
create procedure get_all_area_internship_count()
	begin
		declare city_in varchar(20);
		declare department_in varchar(20);
		declare done int default 0;
		declare area_internship_count int default 0;
		declare cur1 cursor for select distinct city,department from tb_internship_company;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
		delete from statistical_table2;
		open cur1;
		areaLoop:loop
			fetch cur1 into city_in,department_in;
			if done=1
			then leave areaLoop;
			end if;
			select count(distinct(i.sutdent_no)) from tb_internship i,tb_internship_company c where i.internship_company_id = 				c.id and c.city = city_in and c.department = department_in into area_internship_count;
			insert into statistical_table2 values (city_in, department_in,area_internship_count);
		end loop areaLoop;
		close cur1;
		
	end;
#7.n年内至少招收一个实习生的公司
create procedure has_internship_company(in n int)
	begin
	call year_company_internship_count(n);
	select company_name from statistical_table3 where internship_count > 0;
	end;//
 delimiter ;


