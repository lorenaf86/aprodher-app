function handleSubmit(args, dialog, widget) 
{
    if(args.validationFailed) 
    {
        jQuery('#' + dialog).effect('shake', { times:3 }, 100);
    } 
    else {
        PF(widget).hide();
    }
}


function handleClose(dialog)
{
    PF(dialog).hide();
}