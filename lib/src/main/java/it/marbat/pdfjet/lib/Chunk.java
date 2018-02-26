package it.marbat.pdfjet.lib;

import java.util.zip.CRC32;


@SuppressWarnings({"WeakerAccess", "unused"})
class Chunk {

    protected byte[] type;

    private long chunkLength;
    private byte[] data;
    private long crc;
    
    
    public Chunk() {
    }


    public long getLength() {
        return this.chunkLength;
    }


    public void setLength( long chunkLength ) {
        this.chunkLength = chunkLength;
    }


    public void setType( byte[] type ) {
        this.type = type;
    }


    public byte[] getData() {
        return this.data;
    }


    public void setData( byte[] data ) {
        this.data = data;
    }


    public long getCrc() {
        return this.crc;
    }


    public void setCrc( long crc ) {
        this.crc = crc;
    }


    public boolean hasGoodCRC() {
        CRC32 computedCRC = new CRC32();
        computedCRC.update( type, 0, 4 );
        computedCRC.update( data, 0, ( int ) chunkLength );
        return ( computedCRC.getValue() == this.crc );
    }

}   // End of Chunk.java
