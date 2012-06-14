# This dir is used to get axis to load an alternative XMLEcoder for UTF8 so UTF8 characters are not escaped 
# (Since the encoder is used for UTF8 encodings, UTF8 characters are supported and do not need to be escaped in the first place!).
# 
# Axis obviously expects the pluggable encoders to be in a jar file, but this isn't convenient so I've added 
# META-INF/services to the src/classes root instead.