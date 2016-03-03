set names utf8;
use internship_management;
DELIMITER //
CREATE TRIGGER trigger_internship_delete AFTER DELETE ON tb_internship FOR EACH ROW 
BEGIN
  CALL year_internship_pro();
  call year_not_internship_pro();
  call get_all_area_internship_count();
END;
CREATE TRIGGER trigger_internship_ins AFTER insert ON tb_internship FOR EACH ROW 
BEGIN
  CALL year_internship_pro();
  call year_not_internship_pro();
  call get_all_area_internship_count();
END;
CREATE TRIGGER trigger_internship_upd AFTER update ON tb_internship FOR EACH ROW 
BEGIN
  CALL year_internship_pro();
  call year_not_internship_pro();
  call get_all_area_internship_count();
END;//
DELIMITER ;
