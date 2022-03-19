import zipfile

class Create_Zip:

    def Run(self,list_data):
        try:
            import zlib
            compression = zipfile.ZIP_DEFLATED
        except:
            compression = zipfile.ZIP_STORED
        zf = zipfile.ZipFile("./static/Attachments.zip", mode="w")
        for url in list_data:
            zf.write("C:/Users/David/Desktop/hay_profe/b"+str(url), compress_type=compression)
        zf.close()
        return True