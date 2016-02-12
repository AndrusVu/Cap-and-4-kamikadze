from os import listdir
from os.path import isfile, join
from shutil import copyfile

seedPath = "SeedFiles"
seed_files_names = [join(seedPath, f)
                for f in listdir(seedPath) 
                if isfile(join(seedPath, f))]

generate_num = 10000

i = 0;
while True:
  for ref_file_name in seed_files_names:
    new_file_name = "Replay_" + str(i) + "_.repl"
    print(ref_file_name, "->", new_file_name)
    copyfile(ref_file_name, new_file_name)
    i += 1
    if i > generate_num:
      exit()