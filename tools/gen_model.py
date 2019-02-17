import argparse
import cx_Oracle
import jinja2 
import os.path

class Generator:
   def __init__(self, url):
      self.con = cx_Oracle.connect(url)
      self.cur = self.con.cursor()
      #con.close()


   def convert(self, word, trimI3=True):
      val = ''.join(x.capitalize() or '_' for x in word.split('_'))
      return val[2:] if trimI3 and val.startswith('I3') else val

   def getPKColumn(self, table):
      self.cur.execute("""select cols.COLUMN_NAME
         from all_constraints cons,
            all_cons_columns cols
         where cols.TABLE_NAME = :1 and 
            cols.CONSTRAINT_NAME = cons.CONSTRAINT_NAME and
            cons.CONSTRAINT_TYPE = 'P'""", (table,))
      row = self.cur.fetchone()
      return row[0]

   def getDataType(self, dbType, dbPrecision):
      if dbType == 'VARCHAR2':
         return 'String'
      elif dbType == 'DATE':
         return 'Date'
      elif dbType == 'NUMBER':
         if dbPrecision:
            return 'Double'
         else:
            return 'Long'
      else:
         raise Exception('Unknown data type: {}'.format(dbType))

   def getModel(self, table):
      model = {}
      model['db_name'] = table.lower()
      model['name'] = self.convert(table, False)
      cols = []
      

      pkCol = self.getPKColumn(table)
      self.cur.execute("""select COLUMN_NAME, NULLABLE, DATA_TYPE, DATA_PRECISION, DATA_DEFAULT
                  from all_tab_cols 
                  where owner=\'I3\' and 
                     COLUMN_NAME not like '%$%' and
                     table_name=:1
                  order by decode(COLUMN_NAME, :2, 0, COLUMN_ID)""", (table, pkCol))
      for result in self.cur:
         col = {}
         col['is_pk'] = result[0] == pkCol
         col['name'] = 'id' if col['is_pk'] else self.convert(result[0])
         col['cap_name'] = col['name'][0].upper() + col['name'][1:]
         col['db_name'] = result[0].lower()
         col['nullable'] = result[1] == 'Y'
         col['data_type'] = self.getDataType(result[2], result[3])
         col['default'] = result[4].lower() if result[4] else None
         cols.append(col)
      
      model['cols'] = cols
      return model

  
   def store(self, tables, javaSrc):
      env = jinja2.Environment(loader=jinja2.FileSystemLoader(''))
      for table in tables:
         try:
            model = self.getModel(table)
            with open(os.path.join(javaSrc, 'ru/devag/kamc/model', model['name'] + '.java'), 'w') as f:
               f.write(env.get_template('model.j2').render(model))

            with open(os.path.join(javaSrc, 'ru/devag/kamc/repo', model['name'] + 'Repository.java'), 'w') as f:
               f.write(env.get_template('crud.j2').render(model))
            
            print("{}: ok".format(table))
         except:
            print("{}: error".format(table))



#gen = Generator('i3/pandora@127.0.0.1/kamc')
gen = Generator('i3/pandora@ora11g/kamc')

#tables = ['I3_OBJECT', 'I3_CATEGORY', 'I3_RELATION', 'I3_BASEMENT']
#tables = ['I3_CNTR_COMPONENT', 'I3_SUBJECT', 'I3_PERS_COMPONENT', 'I3_CMPY_COMPONENT']
#tables = ['I3_STATUS']
#tables = ['I3_LPTY_COMPONENT']
#tables = ['I3_CLASSIFIER_VALUE']
#tables = ['I3_SBJ_BST', 'I3_SBJ_CONTRACTOR']
#tables = ['I3_OBJ_BST']
#tables = ['I3_NETW_COMPONENT', 'I3_APRM_COMPONENT', 'I3_KFXA_COMPONENT', 'I3_TRAN_COMPONENT', 'I3_BLDN_COMPONENT']
#tables = ['I3_OBJ_RTN', 'I3_SBJ_RTN']
#tables = ['I3_LAND_COMPONENT']
#tables = ['I3_TITL_COMPONENT', 'I3_TRAT_PROPERTY', 'I3_TRAT_COMPONENT']
tables = ['I3_RTN_BST']
#javaSrc = '/home/aklukvin/prj/kamc/web/src/main/java'
javaSrc = 'C:/prj/KAMC/web/src/main/java'
gen.store(tables, javaSrc)

#parser = argparse.ArgumentParser("")