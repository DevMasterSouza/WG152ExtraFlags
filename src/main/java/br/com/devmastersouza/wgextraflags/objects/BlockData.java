package br.com.devmastersouza.wgextraflags.objects;

import org.bukkit.Material;

/**
 * Project WG152ExtraFlags.
 * Create by DevMasterSouza - email: devmastersouza@gmail.com
 */
public class BlockData {

    int id;
    byte data;

    public BlockData(int id, byte data) {
        this.id = id;
        this.data = data;
    }

    public BlockData(Material material, byte data) {this(material.getId(), data);}
    public BlockData(Material material) {this(material, (byte)0);}
    public BlockData(int id) {this(id, (byte)0);}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getData() {
        return data;
    }

    public void setData(byte data) {
        this.data = data;
    }
}
